package ru.shikhovtsev;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.*;

/*
  -Xms512m
  -Xmx512m
  -Xlog:gc=debug:file=./logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
  -XX:+HeapDumpOnOutOfMemoryError
  -XX:HeapDumpPath=./logs/dump


  -XX:+UseG1GC

  {G1 Young Generation=GCInfo{totalDuration=33241, count=788, gcCauses=[G1 Humongous Allocation, G1 Evacuation Pause]},
  G1 Old Generation=GCInfo{totalDuration=4464, count=14, gcCauses=[G1 Humongous Allocation, G1 Evacuation Pause]}}
  time:311


  -XX:+UseSerialGC

  {Copy=GCInfo{totalDuration=8951, count=192, gcCauses=[Allocation Failure]},
  MarkSweepCompact=GCInfo{totalDuration=795231, count=1243, gcCauses=[Allocation Failure]}}
  time:1166


  -XX:+UseParallelGC

  {PS MarkSweep=GCInfo{totalDuration=159390, count=265, gcCauses=[Ergonomics, Allocation Failure]},
  PS Scavenge=GCInfo{totalDuration=70283, count=338, gcCauses=[Allocation Failure]}}
  time:448


  -XX:+UseConcMarkSweepGC
  {ParNew=GCInfo{totalDuration=13224, count=210, gcCauses=[Allocation Failure]},
  ConcurrentMarkSweep=GCInfo{totalDuration=584774, count=658, gcCauses=[No GC, Allocation Failure]}}
  time:894
 */

public class GcDemo {

  public static void main(String[] args) throws InterruptedException {
    System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());
    GcDemo gcDemo = new GcDemo();
    gcDemo.switchOnMonitoring();
    long beginTime = System.currentTimeMillis();

    try {
      gcDemo.run();
    } catch (OutOfMemoryError e) {
      gcDemo.printResult();
    }
    System.out.println("time:" + (System.currentTimeMillis() - beginTime) / 1000);
  }

  private void printResult() {
    System.out.println(gcInfoMap);
  }

  private Map<String, GCInfo> gcInfoMap = new HashMap<>();

  void run() throws InterruptedException {
    List<String> list = new ArrayList<>();
    while (true) {
      int size = 100_000;
      for (int i = 0; i < size; i++) {
        list.add(new String(new char[0]));
      }
      list.subList(0, size - 1100).clear();
      Thread.sleep(10); //Label_1
      System.out.println(list.size());
    }
  }

  private void switchOnMonitoring() {
    List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();

    for (GarbageCollectorMXBean gcbean : gcbeans) {
      System.out.println("GC name:" + gcbean.getName());
      NotificationEmitter emitter = (NotificationEmitter) gcbean;
      NotificationListener listener = (notification, handback) -> {
        if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
          GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
          String gcName = info.getGcName();
          String gcAction = info.getGcAction();
          String gcCause = info.getGcCause();

          long startTime = info.getGcInfo().getStartTime();
          long duration = info.getGcInfo().getDuration();

          System.out.println("start:" + startTime + " Name:" + gcName + ", action:" + gcAction + ", gcCause:" + gcCause + "(" + duration + " ms)");

          gcInfoMap.putIfAbsent(gcName, new GCInfo());

          GCInfo gcInfo = gcInfoMap.get(gcName);
          gcInfo.addDuration(duration);
          gcInfo.addCause(gcCause);
        }
      };
      emitter.addNotificationListener(listener, null, null);
    }
  }

  class GCInfo {
    private long totalDuration = 0;
    private int count = 0;

    private Set<String> gcCauses = new HashSet<>();

    public void addDuration(long duration) {
      count++;
      totalDuration += duration;
    }

    public void addCause(String gcCause) {
      gcCauses.add(gcCause);
    }

    @Override
    public String toString() {
      return "GCInfo{" +
          "totalDuration=" + totalDuration +
          ", count=" + count +
          ", gcCauses=" + gcCauses +
          '}';
    }
  }
}

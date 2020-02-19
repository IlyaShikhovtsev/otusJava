package ru.shikhovtsev.proxy;

import java.util.HashMap;
import java.util.Map;

public class YoutubeCacheProxy implements ThirdPartyYoutubeLib {
  private ThirdPartyYoutubeLib youtubeService;
  private Map<String, Video> cachePopular = new HashMap<String, Video>();
  private Map<String, Video> cacheAll = new HashMap<String, Video>();

  public YoutubeCacheProxy() {
    this.youtubeService = new ThirdPartyYoutubeClass();
  }

  @Override
  public Map<String, Video> popularVideos() {
    if (cachePopular.isEmpty()) {
      cachePopular = youtubeService.popularVideos();
    } else {
      System.out.println("YRetrieved list from cache");
    }
    return cachePopular;
  }

  @Override
  public Video getVideo(String videoId) {
    var video = cacheAll.get(videoId);
    if (video == null) {
      video = youtubeService.getVideo(videoId);
      cacheAll.put(videoId, video);
    } else {
      System.out.println("Retrieved video " + videoId + " from cache");
    }
    return video;
  }
}

package ru.shikhovtsev.otus2;

import java.util.*;

public class DIYArrayList<E> implements List<E> {

    private static int DEFAULT_SIZE = 10;

    private int size = 0;

    private Object[] objects;

    public DIYArrayList() {
        objects = new Object[DEFAULT_SIZE];
    }

    public DIYArrayList(int length) {
        objects = new Object[length];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(objects, size);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(E e) {
        if (objects.length == size) {
            grow(size + 1);
        }
        objects[size] = e;
        size++;
        return true;
    }

    @Override
    public void add(int index, E element) {
        Objects.checkIndex(index, size);
        if (objects.length == size) {
            grow(size + 1);
            System.arraycopy(objects, index, objects, index + 1, size - index);
        }
        objects[index] = element;
        size++;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E e : c) {
            add(e);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        Objects.checkIndex(index, size);
        int newSize = size + c.size();
        if (newSize >= objects.length) {
            grow(newSize);
        }
        System.arraycopy(objects, index, objects, index + c.size(), size - index);
        for (E e : c) {
            add(index++, e);
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = 0;
        for (; index < objects.length; index++) {
            if (objects[index].equals(o)) {
                simpleRemove(index);
                return true;
            }
        }
        return false;
    }

    @Override
    public E remove(int index) {
        Objects.checkIndex(index, size);
        return simpleRemove(index);
    }

    @SuppressWarnings("unchecked")
    private E simpleRemove(int index) {
        E deleted = (E) objects[index];
        int newSize = size - 1;
        if (newSize > index)
            System.arraycopy(objects, index + 1, objects, index, newSize - index);
        size = newSize;
        objects[size] = null;

        return deleted;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for (Object o : c) {
            if (contains(o)) {
                remove(o);
            }
        }
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    private void grow(int newSize) {
//TODO        bad solution
        objects = Arrays.copyOf(objects, newSize);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int index = 0;
        for (int i = 0; i < objects.length; i++) {
            if (!c.contains(objects[i])) {
                index = i;
                break;
            }
        }

        int j = index++;
        for (; index < size; index++) {
            if (c.contains(objects[index])) {
                objects[j++] = objects[index];
            }
        }

        for (int i = j; i < size; i++) {
            objects[i] = null;
        }
        size = j;
        return true;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            objects[i] = null;
        }
        size = 0;
    }

    @Override
    public E get(int index) {
        Objects.checkIndex(index, size);
        return getData(index);
    }

    @Override
    public E set(int index, E element) {
        Objects.checkIndex(index, size);
        objects[index] = element;
        return getData(index);
    }

    @SuppressWarnings("unchecked")
    private E getData(int index) {
        return (E) objects[index];
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < objects.length; i++) {
            if (objects[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = objects.length - 1; i >= 0; i--) {
            if (objects[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new DIYListIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Object object : objects) {
            sb.append(object).append(" ");
        }
        return sb.toString();
    }

    private class DIYListIterator implements ListIterator<E> {
        private int cursor;
        int lastRetIndex = -1;

        DIYListIterator() {
        }

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @SuppressWarnings("unchecked")
        @Override
        public E next() {
            if (cursor >= size) {
                throw new NoSuchElementException();
            }
            Object[] objects = DIYArrayList.this.objects;
            if (cursor >= objects.length)
                throw new ConcurrentModificationException();
            lastRetIndex = cursor;
            return (E) DIYArrayList.this.objects[cursor++];
        }

        @Override
        public void remove() {
            if (lastRetIndex < 0)
                throw new IllegalStateException();

            try {
                DIYArrayList.this.remove(lastRetIndex);
                cursor = lastRetIndex;
                lastRetIndex = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public boolean hasPrevious() {
            return cursor != 0;
        }

        @SuppressWarnings("unchecked")
        @Override
        public E previous() {
            Object[] objects = DIYArrayList.this.objects;
            int prevIndex = cursor - 1;
            if (prevIndex < 0) {
                throw new NoSuchElementException();
            }
            if (prevIndex >= objects.length)
                throw new ConcurrentModificationException();
            cursor = lastRetIndex = prevIndex;
            return (E) objects[lastRetIndex];
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void set(E e) {
            if (lastRetIndex < 0)
                throw new IllegalStateException();

            try {
                DIYArrayList.this.set(lastRetIndex, e);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void add(E e) {
            try {
                int i = cursor;
                DIYArrayList.this.add(i, e);
                cursor = i + 1;
                lastRetIndex = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }
}

package org.example;

public class MyList<T> {

    private Node<T> head;
    private Node<T> tail;
    long listSize;

    public MyList() {
    }

    public long size() {
        return listSize;
    }

    public void addFirst(T el) {
        if(head == null) {
            head = new Node<>(null, null, el);
            tail = head;
        } else {
            Node<T> temp = head;
            head = new Node<>(head, null, el);
            temp.pPrev = head;
        }
        listSize++;
    }

    public void add(long index, T el) {
        if(index < 0 || index > listSize) {
            throw new RuntimeException();
        }
        if(head == null && index == 0) {
            addFirst(el);
            return;
        }
        Node<T> temp;
        Node<T> ded;
        if(listSize - index >= listSize / 2) {
            temp = head;
            ded = head;
            for (long i = 0L; i < index; i++) {
                ded = temp;
                temp = temp.pNext;
            }
        } else {
            temp = tail;
            ded = tail;
            for(long i = listSize - 1; i > index + 1; i--) {
                ded = temp;
                temp = temp.pPrev;
            }
        }
        if(ded.pNext == null) {
            addLast(el);
        } else if(ded.pPrev == null) {
            addFirst(el);
        }
        else {
            temp.value = el;
            listSize++;
        }
    }

    public void addLast(T el) {
        if(head == null) {
            head = new Node<>(null, null, el);
            tail = head;
        } else {
            Node<T> temp = tail;
            tail = new Node<>(null, tail, el);
            temp.pNext = tail;
        }
        listSize++;
    }

    public T getFirst() {
        if(head == null) {
            throw new RuntimeException();
        }
        return head.value;
    }

    public T getLast() {
        if(tail == null) {
            throw new RuntimeException();
        }
        return tail.value;
    }

    public T get(long index) {
        if(index < 0 || index >= listSize) {
            throw new RuntimeException();
        }
        Node<T> temp;
        if(listSize - index >= listSize / 2) {
            temp = head;
            for(long i = 0L; i < index; i++) {
                temp = temp.pNext;
            }
            return temp.value;
        } else {
            temp = tail;
            for(long i = listSize - 1; i > index; i--) {
                temp = temp.pPrev;
            }
            return temp.value;
        }
    }

    public T remoteFirst() {
        if(head == null) {
            throw new RuntimeException();
        }
        T el = getFirst();
        if(head.pNext == null) {
            tail = null;
        } else {
            head.pNext.pPrev = null;
        }
        head = head.pNext;
        listSize--;
        return el;
    }

    public T remoteLast() {
        if(head == null) {
            throw new RuntimeException();
        }
        T el = getLast();
        if(tail.pPrev == null) {
            head = null;
        } else {
            tail.pPrev.pNext = null;
        }
        tail = tail.pPrev;
        listSize--;
        return el;
    }

    public T remote(long index) {
        if(index < 0 || index > listSize - 1) {
            throw new RuntimeException();
        }
        Node<T> temp;
        if(listSize - index >= listSize / 2) {
            temp = head;
            for(long i = 0L; i < index; i++) {
                temp = temp.pNext;
            }
        } else {
            temp = tail;
            for(long i = listSize - 1; i > index; i--) {
                temp = temp.pPrev;
            }
        }
        T el = get(index);
        if(temp.pNext == null) {
            remoteLast();
        } else if(temp.pPrev == null) {
            remoteFirst();
        } else {
            temp.pPrev.pNext = temp.pNext;
            temp.pNext.pPrev = temp.pPrev;
            listSize--;
        }
        return el;
    }

    private static class Node<T> {
        public T value;
        public Node<T> pNext;
        public Node<T> pPrev;

        public Node(Node<T> pNext, Node<T> pPrev, T value) {
            this.pNext = pNext;
            this.pPrev = pPrev;
            this.value = value;
        }
    }

}

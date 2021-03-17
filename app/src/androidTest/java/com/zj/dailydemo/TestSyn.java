package com.zj.dailydemo;

class TestSyn {
    private TestSyn testSyn = new TestSyn();

    private synchronized void getName() {

    }

    public void getAge() {
        synchronized (TestSyn.class) {

        }
        synchronized (testSyn){

        }
    }
}

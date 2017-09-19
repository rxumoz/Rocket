package org.mozilla.focus.screenshot;

public interface CaptureCallback {
    void onCaptureStart();

    void onCaptureSuccess(String filePath);

    void onCaptureFail();

    abstract class CaptureCallbackAdapter implements CaptureCallback {

        @Override
        public void onCaptureStart() {

        }

        @Override
        public void onCaptureSuccess(String filePath) {

        }

        @Override
        public void onCaptureFail() {

        }
    }


}

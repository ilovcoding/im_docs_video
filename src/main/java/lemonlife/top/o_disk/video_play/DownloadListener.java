package lemonlife.top.o_disk.video_play;

public interface DownloadListener {

    void start();

    void process(String downloadUrl, int finished, int sum, float percent);

    void speed(String speedPerSecond);

    void end();

}

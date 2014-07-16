package sg.edu.nus.comp.klttracker;

import android.graphics.Bitmap;

import org.ddogleg.struct.FastQueue;

import java.util.ArrayList;
import java.util.List;

import boofcv.abst.feature.tracker.PointTrack;
import boofcv.abst.feature.tracker.PointTracker;
import boofcv.struct.image.ImageUInt8;
import georegression.struct.point.Point2D_F64;

/**
 * Created by panlong on 16/7/14.
 */
class PointProcessing {
    PointTracker<ImageUInt8> tracker;

    long tick;

    List<PointTrack> active = new ArrayList<PointTrack>();
    List<PointTrack> spawned = new ArrayList<PointTrack>();
    List<PointTrack> inactive = new ArrayList<PointTrack>();

    // storage for data structures that are displayed in the GUI
    FastQueue<Point2D_F64> trackSrc = new FastQueue<Point2D_F64>(Point2D_F64.class, true);
    FastQueue<Point2D_F64> trackDst = new FastQueue<Point2D_F64>(Point2D_F64.class, true);
    FastQueue<Point2D_F64> trackSpawn = new FastQueue<Point2D_F64>(Point2D_F64.class, true);


    public PointProcessing(PointTracker<ImageUInt8> tracker) {
        this.tracker = tracker;
    }

    protected void process(ImageUInt8 gray) {
        tracker.process(gray);

        // drop tracks which are no longer being used
        inactive.clear();
        tracker.getInactiveTracks(inactive);
        for (int i = 0; i < inactive.size(); i++) {
            PointTrack t = inactive.get(i);
            TrackInfo info = t.getCookie();
            if (tick - info.lastActive > 2) {
                tracker.dropTrack(t);
            }
        }

        active.clear();
        tracker.getActiveTracks(active);
        for (int i = 0; i < active.size(); i++) {
            PointTrack t = active.get(i);
            TrackInfo info = t.getCookie();
            info.lastActive = tick;
        }

        spawned.clear();
        if (active.size() < 50) {
            tracker.spawnTracks();

            // update the track's initial position
            for (int i = 0; i < active.size(); i++) {
                PointTrack t = active.get(i);
                TrackInfo info = t.getCookie();
                info.spawn.set(t);
            }

            tracker.getNewTracks(spawned);
            for (int i = 0; i < spawned.size(); i++) {
                PointTrack t = spawned.get(i);
                if (t.cookie == null) {
                    t.cookie = new TrackInfo();
                }
                TrackInfo info = t.getCookie();
                info.lastActive = tick;
                info.spawn.set(t);
            }
        }

        trackSrc.reset();
        trackDst.reset();
        trackSpawn.reset();

        for (int i = 0; i < active.size(); i++) {
            PointTrack t = active.get(i);
            TrackInfo info = t.getCookie();
            Point2D_F64 s = info.spawn;
            Point2D_F64 p = active.get(i);

            trackSrc.grow().set(s);
            trackDst.grow().set(p);
        }

        for (int i = 0; i < spawned.size(); i++) {
            Point2D_F64 p = spawned.get(i);
            trackSpawn.grow().set(p);
        }

        tick++;
    }

    public FastQueue<Point2D_F64> getTrackSrc() {
        return trackSrc;
    }

    public FastQueue<Point2D_F64> getTrackDst() {
        return trackDst;
    }

    public FastQueue<Point2D_F64> getTrackSpawn() {
        return trackSpawn;
    }
}

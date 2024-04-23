import java.util.ArrayList;
import java.util.List;

public class PlaylistManager {

    private List<String> playlist;

    public PlaylistManager() {
        this.playlist = new ArrayList<>();
    }

    public void addToPlaylist(String musicId) {
        playlist.add(musicId);
    }

    public void removeFromPlaylist(String musicId) {
        playlist.remove(musicId);
    }

    public List<String> getPlaylist() {
        return playlist;
    }

   
}

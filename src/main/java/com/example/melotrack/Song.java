package com.example.melotrack;

public class Song {
    private String songTitle;
    private String songArtist;
    private String songAlbum;
    private float songLength;

    public Song(String songTitle, String songArtist, String songAlbum, float songLength) {
        this.songTitle = songTitle;
        this.songArtist = songArtist;
        this.songAlbum = songAlbum;
        this.songLength = songLength;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }

    public String getSongAlbum() {
        return songAlbum;
    }

    public void setSongAlbum(String songAlbum) {
        this.songAlbum = songAlbum;
    }

    public float getSongLength() {
        return songLength;
    }

    public void setSongLength(float songLength) {
        this.songLength = songLength;
    }
}

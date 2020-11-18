package com.mycompany.rdfgenerator;

import com.opencsv.CSVReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author fernandalopezgallegos
 */
public class Main {

    public static void main(String[] args) throws IOException {
//        
        System.out.println("***************************************************");
        System.out.println("Ranking Billboard 1964 - 2015 y Música de Spotify");
        System.out.println("***************************************************");
        
        String billboardPath = "billboard_lyrics_1964-2015.csv";
        String spotifyPath = "SpotifyFeatures.csv";
        createBillboardRDF(billboardPath, args);
        createSpotifyRDF(spotifyPath, args);
    }

    private static void createBillboardRDF(String billboardPath, String[] args) throws IOException {
        String rank;
        String track_name;
        String artist_name;
        String year;
        String lyrics;
        String source;   
        int count = 0;
        BillboardRDF billboard = new BillboardRDF();
        System.out.println("***************************************************");
        System.out.println("Creando modelo de BillboardRDF...");        
        System.out.println("***************************************************");
        try (
                Reader reader = Files.newBufferedReader(Paths.get(billboardPath));
                CSVReader csvReader = new CSVReader(reader);) {
            // Reading Records One by One in a String array
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                
                rank = nextRecord[0];
                track_name = nextRecord[1];
                artist_name = nextRecord[2];
                year = nextRecord[3];
                lyrics = nextRecord[4];
                source = nextRecord[5];
                if(count!=0){
                    billboard.createModel(rank, track_name, artist_name, year, lyrics, source);
                }
                count++;
            }
            //billboard.writeModel();
            if(null!=args[0])switch (args[0]) {
                case "1":
                    System.out.println("Creando archivo Turtle de BillboardRDF...");
                    System.out.println("***************************************************");
                    billboard.createFile(1);
                    break;
                case "2":
                    System.out.println("Creando archivo RDF/XML de BillboardRDF...");
                    System.out.println("***************************************************");
                    billboard.createFile(2);
                    break;
                case "3":
                    System.out.println("Creando archivo N-TRIPLES de BillboardRDF...");
                    System.out.println("***************************************************");
                    billboard.createFile(3);
                    break;
                default:
                    break;
            }
        }
    }
    private static void createSpotifyRDF(String spotifyPath, String[] args) throws IOException {
        String song;
        String song_id;
        String popularity;
        String acousticness;
        String danceability;
        String duration_ms;
        String energy;
        String instrumentalness;
        String key;
        String liveness;
        String loudness;
        String mode;
        String speechiness;
        String tempo;
        String time_signature;
        String valence;
        String genre;
        String artist;
        int count = 0;
        SpotifyRDF spotify = new SpotifyRDF();
        System.out.println("***************************************************");
        System.out.println("Creando modelo de SpotifyRDF...");        
        System.out.println("***************************************************");
        try ( Reader reader = Files.newBufferedReader(Paths.get(spotifyPath));
                CSVReader csvReader = new CSVReader(reader);) {
            // Reading Records One by One in a String array
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                genre = nextRecord[0];
                artist = nextRecord[1];
                song = nextRecord[2];
                song_id = nextRecord[3];
                popularity = nextRecord[4];
                acousticness = nextRecord[5];
                danceability = nextRecord[6];
                duration_ms = nextRecord[7];
                energy = nextRecord[8];
                instrumentalness = nextRecord[9];
                key = nextRecord[10];
                liveness = nextRecord[11];
                loudness = nextRecord[12];
                mode = nextRecord[13];
                speechiness = nextRecord[14];
                tempo = nextRecord[15];
                time_signature = nextRecord[16];
                valence = nextRecord[17];
                
                if(count!=0){
                    spotify.createModel(song, song_id, popularity, acousticness, danceability, duration_ms, energy, instrumentalness, key, liveness, loudness, mode, speechiness, tempo, time_signature, valence, genre, artist);
                }
                count++;
            }
            //spotify.writeModel();
            if(null!=args[0])switch (args[0]) {
                case "1":
                    System.out.println("Creando archivo Turtle de SpotifyRDF...");
                    System.out.println("***************************************************");
                    spotify.createFile(1);
                    break;
                case "2":
                    System.out.println("Creando archivo RDF/XML de SpotifyRDF...");
                    System.out.println("***************************************************");
                    spotify.createFile(2);
                    break;
                case "3":
                    System.out.println("Creando archivo N-TRIPLES de SpotifyRDF...");
                    System.out.println("***************************************************");
                    spotify.createFile(3);
                    break;
                default:
                    break;
            }
            System.out.println("***************************************************");
        }
    }
}

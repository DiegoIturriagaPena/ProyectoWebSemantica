/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rdfgenerator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.RDFWriter;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;

/**
 *
 * @author Diego
 */
public class SpotifyRDF {
    
    private Model model;
    private RDFWriter writer;
    
    private String rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    private String rdfs = "http://www.w3.org/2000/01/rdf-schema#";
    private String voc = "http://example.org/spotify/voc#";
    private String spo = "http://spotify.com/";

    public SpotifyRDF() {
        model = ModelFactory.createDefaultModel();
        writer = model.getWriter();
    }
    
    public void writeModel(){
        model.setNsPrefix("rdf", rdf);
        model.setNsPrefix("rdfs", rdfs);
        model.setNsPrefix("voc", voc);
        model.setNsPrefix("spo", spo);
        model.write(System.out,"Turtle");
    }
    /**
     * Agrega un Literal como parte de los triples (el ultimo).
     * @param s
     * @param p
     * @param o 
     */
    public void addStatement(String s, String p, String o){
        Resource subject = model.createResource(s);
        Property predicate = model.createProperty(p);
        RDFNode literal = model.createLiteral(o);
        Statement stmt = model.createStatement(subject, predicate, literal);
        model.add(stmt);
    }
    /**
     * Agrega un Resource entre los elementos del triple (el ultimo).
     * @param s
     * @param p
     * @param o 
     */
    public void addStatementObject(String s, String p, String o){
        Resource subject = model.createResource(s);
        Property predicate = model.createProperty(p);
        RDFNode object = model.createResource(o);
        Statement stmt = model.createStatement(subject, predicate, object);
        model.add(stmt);
    }
    
    public void createModel(String song, String song_id, String popularity, String acousticness,
            String danceability, String duration_ms, String energy, String instrumentalness,String key,
            String liveness,String loudness,String mode,String speechiness,String tempo,String time_signature,
            String valence,String genre,String artist){
        
        addStatementObject(spo+song_id, rdf+"type", voc+"TrackSpotify");
        addStatement(spo+song_id, voc+"Track_name_Spo", song);
        addStatement(spo+song_id, voc+"Track_id", song_id);
        addStatement(spo+song_id, voc+"Popularity", popularity);
        addStatement(spo+song_id, voc+"Acousticness", acousticness);
        addStatement(spo+song_id, voc+"Danceability", danceability);
        addStatement(spo+song_id, voc+"Duration_ms", duration_ms);
        addStatement(spo+song_id, voc+"Energy", energy);
        addStatement(spo+song_id, voc+"Instrumentalness", instrumentalness);
        addStatement(spo+song_id, voc+"Key", key);
        addStatement(spo+song_id, voc+"Liveness", liveness);
        addStatement(spo+song_id, voc+"Loudness", loudness);
        addStatement(spo+song_id, voc+"Mode", mode);
        addStatement(spo+song_id, voc+"Speechiness", speechiness);
        addStatement(spo+song_id, voc+"Tempo", tempo);
        addStatement(spo+song_id, voc+"Time_signature", time_signature);
        addStatement(spo+song_id, voc+"Valence", valence);
        
        String genre2 = genre.replace(" ", "_");
        
        addStatementObject(spo+genre2, rdf+"type", voc+"Genre");
        addStatement(spo+genre2, voc+"Genre_name", genre);
        
        addStatementObject(spo+song_id, voc+"belongsTo", spo+genre2);
        //falta el simbolo de ° con la raya inferior.
        String artist2 = artist.replace(" ", "_");
        artist2 = artist2.replace("\"" , "_");
        artist2 = artist2.replace("^" , "_");
        artist2 = artist2.replace("µ" , "u");
        artist2 = artist2.replace("[" , "_");
        artist2 = artist2.replace("]" , "_");
        
        addStatementObject(spo+artist2, rdf+"type", voc+"ArtistSpotify");
        addStatement(spo+artist2, voc+"Artist_name_Spo", artist);
        addStatementObject(spo+artist2, voc+"hasPlaySpo", spo+song_id);
        
    }
    
    public void createFile(int option) throws FileNotFoundException{
        model.setNsPrefix("rdf", rdf);
        model.setNsPrefix("rdfs", rdfs);
        model.setNsPrefix("voc", voc);
        model.setNsPrefix("spo", spo);
        OutputStream out;
        switch (option) {
            case 1:
                out = new FileOutputStream("Spotify.ttl");
                model.write(out, "Turtle");
                System.out.println("---Archivo Spotify.ttl creado---");
                break;
            case 2:
                out = new FileOutputStream("Spotify.rdf");
                writer.write(model, out, rdf+","+rdfs+","+voc+","+spo);
                System.out.println("---Archivo Spotify.rdf creado---");
                break;
            case 3:
                out = new FileOutputStream("Spotify.nt");
                model.write(out, "N-TRIPLES");
                System.out.println("---Archivo Spotify.nt creado---");
                break;
            default:
                break;
        }
    }
}

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
 * @author fernandalopezgallegos
 */
class BillboardRDF {

    private Model model;
    private RDFWriter writer;

    private String rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    private String rdfs = "http://www.w3.org/2000/01/rdf-schema#";
    private String voc = "http://example.org/billboard/voc#";
    private String bb = "http://billboard.com/";

    public BillboardRDF() {
        model = ModelFactory.createDefaultModel();
        writer = model.getWriter();
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

    void createModel(String rank, String track_name, String artist_name, String year, String lyrics, String source) {
        
        addStatementObject(bb+year+"-"+rank, rdf+"type", voc+"TrackBillboard");
        addStatement(bb+year+"-"+rank, voc+"Track_name_BB", track_name);
        addStatement(bb+year+"-"+rank, voc+"Ranking", rank);
        addStatement(bb+year+"-"+rank, voc+"Year", year);
        addStatement(bb+year+"-"+rank, voc+"Lyrics", lyrics);
        addStatement(bb+year+"-"+rank, voc+"Source", source);
        
        String artist_name2 = artist_name.replace(" ", "_");
        artist_name2 = artist_name2.replace("\"" , "_");
        
        addStatementObject(bb+artist_name2, rdf+"type", voc+"ArtistBillboard");
        addStatement(bb+artist_name2, voc+"Artist_name_BB", artist_name);
        addStatementObject(bb+artist_name2, voc+"hasPlayBB", bb+year+"-"+rank);
    }

    void writeModel() {
        model.setNsPrefix("rdf", rdf);
        model.setNsPrefix("rdfs", rdfs);
        model.setNsPrefix("voc", voc);
        model.setNsPrefix("bb", bb);
        model.write(System.out, "Turtle");
    }
    
    public void createFile(int option) throws FileNotFoundException{
        model.setNsPrefix("rdf", rdf);
        model.setNsPrefix("rdfs", rdfs);
        model.setNsPrefix("voc", voc);
        model.setNsPrefix("bb", bb);
        
        OutputStream out;
        switch (option) {
            case 1:
                out = new FileOutputStream("Billboard.ttl");
                model.write(out, "Turtle");
                System.out.println("---Archivo Billboard.ttl creado---");
                break;
            case 2:
                out = new FileOutputStream("Billboard.rdf");
                writer.write(model, out, rdf+","+rdfs+","+voc+","+bb);
                System.out.println("---Archivo Billboard.rdf creado---");
                break;
            case 3:
                out = new FileOutputStream("Billboard.nt");
                model.write(out, "N-TRIPLES");
                System.out.println("---Archivo Billboard.nt creado---");
                break;
            default:
                break;
        }
    }
}

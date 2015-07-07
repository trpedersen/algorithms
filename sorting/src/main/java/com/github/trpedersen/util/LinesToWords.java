package com.github.trpedersen.util;

import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.*;
import java.util.regex.Pattern;

/**
 * Created by timpe_000 on 6/07/2015.
 */
public class LinesToWords {

    private static void linesToWords() throws IOException {

        String token;
        Writer w = new OutputStreamWriter(System.out);
        BufferedWriter writer = new BufferedWriter(w);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StandardTokenizer tokenizer = new StandardTokenizer();
        tokenizer.setReader(reader);
        tokenizer.reset();

        while (tokenizer.incrementToken()) {
            token = ((CharTermAttribute) tokenizer.getAttribute(CharTermAttribute.class)).toString();
            writer.write(token);
            writer.write("\n");
        }
        tokenizer.end();
        tokenizer.close();
        writer.flush();
        writer.close();
    }

    private static void linesToWords2() throws IOException {

        Pattern pattern = Pattern.compile("[\\s\\.,\\-!;\\?:\\(\\)\"]");
        Writer w = new OutputStreamWriter(System.out);
        BufferedWriter writer = new BufferedWriter(w);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String line;
        while ((line = reader.readLine()) != null) {
            String[] tokens = pattern.split(line);
            for (String token : tokens) {
                if(!token.isEmpty()) {
                    writer.write(token);
                    writer.write("\n");
                }
            }
        }
        writer.flush();
        writer.close();
    }

    public static void main(String[] args) throws IOException {
        linesToWords2();
    }
}

/*
 * MIT License
 *
 * Copyright (c) 2017 The ISSTAC Authors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package e1e4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import edu.columbia.cs.psl.phosphor.runtime.Taint;
import edu.vanderbilt.taintalyzer.tainter.RecursiveMultiTainterBFS;

public class Category4_vulnerable {
    private static final int port = 8000;
    private static final String password = "mypassword";
    //This is the worst case password, for shorter passwords there would be padding at the end.
    private static ServerSocket server;

    private static boolean verifyCredentials(String candidate){
        for(int x = 0; x<candidate.length();x++) {
            if(x>=password.length()||password.charAt(x) != candidate.charAt(x)){
                return false;
            }
            delay();
        }
        return candidate.length() == password.length();
    }
    private static void delay() {for (int x = 0 ; x < 4 ; x++) {}}
    private static boolean doProcess(String userInput){
        // Check password for process 1
        verifyCredentials(userInput);
        // If correct do process 1

        // Check password for process 2
        verifyCredentials(userInput);
        // If correct do process 2

        // Check password for process 3
        verifyCredentials(userInput);
        // If correct do process 3

        // Check password for process 4
        verifyCredentials(userInput);
        // If correct do process 4

        // Check password for process 5
        verifyCredentials(userInput);
        // If correct do process 5

        // Check password for process 6
        verifyCredentials(userInput);
        // If correct do process 6

        // Check password for process 7
        verifyCredentials(userInput);
        // If correct do process 7

        // Check password for process 8
        verifyCredentials(userInput);
        // If correct do process 8

        // Check password for process 9
        verifyCredentials(userInput);
        // If correct do process 9

        // Check password for process 10
        boolean correctPassword = verifyCredentials(userInput);
        // If correct do process 10
        return correctPassword;
    }
    private static void startServer(){
        try {
            server = new ServerSocket(port);
            Socket client;
            PrintWriter out;
            BufferedReader in;
            String userInput;
            while (true) {
                client = server.accept();
                out = new PrintWriter(client.getOutputStream(), true);
                in = new BufferedReader( new InputStreamReader( client.getInputStream()));
                RecursiveMultiTainterBFS tainter = new RecursiveMultiTainterBFS(1, Integer.MAX_VALUE);
            		try {
            			tainter.taintObjects(in, new Taint<String>("tainted_buffer_reader"));
            		} catch (ArrayIndexOutOfBoundsException e) {
            			// TODO Auto-generated catch block
            			e.printStackTrace();
            		} catch (IllegalArgumentException e) {
            			// TODO Auto-generated catch block
            			e.printStackTrace();
            		} catch (Exception e) {
            			// TODO Auto-generated catch block
            			e.printStackTrace();
            		}
                userInput = in.readLine();
                if (userInput.length() <= 10 && userInput.matches("[a-z]+")){
                    if(doProcess(userInput)){
                        out.println("Correct");
                    } else{
                        out.println("Incorrect");
                    }
                }
                else {
                  out.println("Invalid Input");
                }
                client.shutdownOutput();
                client.shutdownInput();
                client.close();
            }
        } catch(IOException e) {
            System.exit(-1);
        }
    }
    public static void main(String[] args) {
        startServer();
    }
}

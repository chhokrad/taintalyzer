package edu.vanderbilt.taintalyzer.test;

import static org.junit.Assert.*;

import java.net.DatagramPacket;

import org.junit.Test;

import edu.columbia.cs.psl.phosphor.runtime.Taint;
import edu.vanderbilt.taintalyzer.tainter.RecursiveMultiTainterBFS;

public class RecursiveMultiTainterBFSjavUtilTest {

	@Test
	public void test() {
		DatagramPacket packetIn = new DatagramPacket(new byte[10], 10);
        RecursiveMultiTainterBFS tainter = new RecursiveMultiTainterBFS(1, Integer.MAX_VALUE);
        try {
                tainter.taintObjects(packetIn, new Taint<String>("tainted_packet"));
                
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
		
	}

}

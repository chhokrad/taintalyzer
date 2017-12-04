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
import edu.columbia.cs.psl.phosphor.runtime.Taint;
import edu.vanderbilt.taintalyzer.tainter.RecursiveMultiTainterBFS;
//Coefficients are Disregarded
public class Category1_vulnerable {
  private static final int secret = 1234;
  private static final int n = 32;
	private static void checkSecret(int guess) throws InterruptedException {
		if(guess <= secret){
			for(int i=0;i<n;i++){
				for(int t=0;t<n;t++) {
					Thread.sleep(1);
				}
			}
		}
		else{
			for(int i=0;i<n;i++){
				for(int t=0;t<n;t++) {
					Thread.sleep(2);
				}
			}
		}
	}
	public static void main(String[] args) throws InterruptedException {
    RecursiveMultiTainterBFS tainter = new RecursiveMultiTainterBFS(1, Integer.MAX_VALUE);
		try {
			tainter.taintObjects(args, new Taint<String>("tainted_args"));
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
		int guess = Integer.parseInt(args[0]);
		checkSecret(guess);
  }
}

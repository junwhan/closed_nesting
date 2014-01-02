/*
 * Aleph Toolkit
 *
 * Copyright 1999, Brown University, Providence, RI.
 * 
 *                         All Rights Reserved
 * 
 * Permission to use, copy, modify, and distribute this software and its
 * documentation for any purpose other than its incorporation into a
 * commercial product is hereby granted without fee, provided that the
 * above copyright notice appear in all copies and that both that
 * copyright notice and this permission notice appear in supporting
 * documentation, and that the name of Brown University not be used in
 * advertising or publicity pertaining to distribution of the software
 * without specific, written prior permission.
 * 
 * BROWN UNIVERSITY DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE,
 * INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY
 * PARTICULAR PURPOSE.  IN NO EVENT SHALL BROWN UNIVERSITY BE LIABLE FOR
 * ANY SPECIAL, INDIRECT OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 * OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package aleph.comm.message;
import aleph.Aleph;
import aleph.PE;
import aleph.comm.Client;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javax.swing.JOptionPane;

/**
 * Send text with Information message.
 *   
 * @date   August 1999
 * @author Maurice Herlihy
 **/
public class Inform extends aleph.Message implements Externalizable {

  private String string;
  private PE pe;

  public Inform (String string) {
    this.string = string;
    this.pe     = PE.thisPE();
  }

  public Inform () {}
  public void run () {
    if (! Aleph.isBatch() && Aleph.getPopup())
      JOptionPane.showMessageDialog(null,
                                    string,
                                    "Information from " + pe,
                                    JOptionPane.INFORMATION_MESSAGE);
    else
      System.err.println("Information from " + pe + ": " + string);
  }

  public String toString () {
    return "Inform[from: " + from + ", string: " + string + "]";
  }

  /**
   * @see java.io.Externalizable
   **/
  public void writeExternal (ObjectOutput out) throws IOException {
    super.writeExternal(out);
    out.writeObject(string);
    out.writeObject(pe);
  }

  /**
   * @see java.io.Externalizable
   **/
  public void readExternal (ObjectInput in)
    throws IOException, java.lang.ClassNotFoundException {
    super.readExternal(in);
    string = (String) in.readObject();
    pe     = (PE) in.readObject();
  }
}

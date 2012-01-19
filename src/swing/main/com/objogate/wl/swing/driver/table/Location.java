/**
 * Something that can be converted into a Cell in a Table 
 */
package com.objogate.wl.swing.driver.table;

import javax.swing.JTable;

public interface Location {
  Cell asCellIn(JTable table);
}
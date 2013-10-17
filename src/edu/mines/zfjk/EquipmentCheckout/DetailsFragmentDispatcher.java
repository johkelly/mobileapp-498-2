package edu.mines.zfjk.EquipmentCheckout;

/**
 * Created with IntelliJ IDEA.
 * User: jack
 * Date: 10/16/13
 * Time: 11:11 PM
 *
 * This went in a separate file to resolve a cyclic dependency issue.
 */
public interface DetailsFragmentDispatcher {
    void displayDetailsFor(int pos);
}

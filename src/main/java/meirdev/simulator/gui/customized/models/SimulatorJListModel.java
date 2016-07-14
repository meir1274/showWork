/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.gui.customized.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.AbstractListModel;
import javax.swing.ListModel;
import meirdev.simulator.gui.customized.rendereners.ActivityType;

import meirdev.simulator.logical.enums.ImpactType;
import meirdev.simulator.logical.enums.RefType;
import meirdev.simulator.gui.interfaces.utils.WrapHashMap;

/**
 *
 * @author MEIRKA
 */
public class SimulatorJListModel extends AbstractListModel implements ListModel {

    private Object selectedItem;
    private Map values;

    private ArrayList anArrayList;
    private int lastChangedValue;

    private Map<ImpactType, Set<Integer>> impactListMap;

    public int getLastChangedValue() {
        return lastChangedValue;
    }

    public void setLastChangedValue(int lastChangedValue) {
        this.lastChangedValue = lastChangedValue;
    }

    public SimulatorJListModel(Object arrayList) {
        if (arrayList instanceof ArrayList) {
            anArrayList = (ArrayList) arrayList;
        } else if (arrayList instanceof WrapHashMap) {
            Map m = (Map) ((WrapHashMap) arrayList).getCurrMap();
            anArrayList = ((WrapHashMap) arrayList).getRealList();
            values = m;
        }
    }

    public int size() {
        return anArrayList.size();
    }

    private SimulatorJListModel() {
        impactListMap = new HashMap<>();
    }

    public Object getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Object newValue) {
        selectedItem = newValue;
    }

    public int getSize() {
        return anArrayList.size();
    }

    public void updateAnotherCell(int index) {
        lastChangedValue = index;
        this.fireContentsChanged(this, index, index);
    }

    public String getIntElementAt(int i, RefType rt) {
        if (values == null) {
            return (String) getElementAt(i);
        }
        String currValue = null;
        if (rt.equals(RefType.RT_REF)) {
            currValue = (String) getElementAt(i);
        } else {
            currValue = Integer.toString(i);
        }

        String keyValue = currValue;
        return (String) values.get(keyValue);
    }

    public Object getElementAt(int i) {

        return anArrayList.get(i);
    }

    public void updateValue(int index, String newValue, String origValue) {
        if (values != null) {
            values.put(newValue, origValue);
        }
        anArrayList.remove(index);
        anArrayList.add(index, newValue);
    }

    /**
     *
     * @param index
     * @param activity
     * @param desc
     */
    public void viewActivity(int index, ActivityType activity, String desc) {

    }

    public void updateAnotherCell(int value, ImpactType impactType) {
        HashSet<Integer> listForType = (HashSet<Integer>) impactListMap.get(impactType);
        if (listForType == null) {
            listForType = new HashSet<Integer>();
            impactListMap.put(impactType, listForType);
        }

        listForType.clear();
        listForType.add(Integer.valueOf(value));

    }

}

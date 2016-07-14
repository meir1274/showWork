/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.debugger;

import java.beans.PropertyChangeSupport;
import java.util.Collections;
import java.util.Set;
import meirdev.simulator.simeasy8.SimpleCmdDetails;

/**
 *
 * @author MEIRKA
 */
public class Breakpoint {
    private SimpleCmdDetails linkToLine;
    
    
    
    public static final String PROP_ENABLED = "enabled";
    public static final String PROP_DISPOSED = "disposed";
    public static final String PROP_GROUP_NAME = "groupName";
    public static final String PROP_GROUP_PROPERTIES = "groupProperties";
    public static final String PROP_VALIDITY = "validity";
    public static final String PROP_HIT_COUNT_FILTER = "hitCountFilter";
    private PropertyChangeSupport pcs;
    private String groupName;
    private VALIDITY validity;
    private String validityMessage;
    private int hitCountFilter;
    private HIT_COUNT_FILTERING_STYLE hitCountFilteringStyle;
    private volatile Set<Breakpoint> breakpointsToEnable;
    private volatile Set<Breakpoint> breakpointsToDisable;

    public static enum VALIDITY {
        UNKNOWN, VALID, INVALID;

        private VALIDITY() {
        }
    }

    public static enum HIT_COUNT_FILTERING_STYLE {
        EQUAL, GREATER, MULTIPLE;

        private HIT_COUNT_FILTERING_STYLE() {
        }
    }

    public Breakpoint() {
        this.groupName = "";
        this.validity = VALIDITY.UNKNOWN;

        this.breakpointsToEnable = Collections.EMPTY_SET;
        this.breakpointsToDisable = Collections.EMPTY_SET;

        this.pcs = new PropertyChangeSupport(this);
    }

    public SimpleCmdDetails getLinkToLine() {
        return linkToLine;
    }

    public void setLinkToLine(SimpleCmdDetails linkToLine) {
        this.linkToLine = linkToLine;
    }
    
    
    
    
}

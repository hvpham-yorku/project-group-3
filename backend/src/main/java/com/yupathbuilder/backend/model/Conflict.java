package com.yupathbuilder.backend.model;


/**
 * Domain model: Conflict.
 *
 * <p>Simple immutable data structures used throughout the backend.
 */

public class Conflict {
    private Section existing;
    private Section incoming;
    private TimeSlot clashOn;

    public Conflict() {}

    public Conflict(Section existing, Section incoming, TimeSlot clashOn) {
        this.existing = existing;
        this.incoming = incoming;
        this.clashOn = clashOn;
    }

    public Section getExisting() { return existing; }
    public void setExisting(Section existing) { this.existing = existing; }

    public Section getIncoming() { return incoming; }
    public void setIncoming(Section incoming) { this.incoming = incoming; }

    public TimeSlot getClashOn() { return clashOn; }
    public void setClashOn(TimeSlot clashOn) { this.clashOn = clashOn; }
}

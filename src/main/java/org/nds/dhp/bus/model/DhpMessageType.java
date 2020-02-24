package org.nds.dhp.bus.model;

public enum DhpMessageType {

    DVA_NOTIF("DvaNotif"), DVA_NOTIF_R("DvaNotifR"), DVA_NOTIF_R_RESPONSE("DvaNotifR_Response"), DVA_NOTIF_RESPONSE("DvaNotif_Response"),
    COPD_REPORT("COPD_Report"),MCO_PROM_OVRN_CNCR("MCO_PROM_OvrnCncr"),TRAUMA_INCIDENT_REPORT("TraumaIncidentReport"), DVA_NOTIF_SMOKE_TEST("DvaNotifSmokeTest");

    private String value;

    DhpMessageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

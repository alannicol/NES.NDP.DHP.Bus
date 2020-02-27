package org.nds.dhp.bus.service;

import org.nds.dhp.bus.model.DhpMessageType;

import java.util.Calendar;
import java.util.UUID;

public class BlobService {

    public static String generateBlobReference(DhpMessageType dhpMessageType) {
        Calendar calendar = Calendar.getInstance();

        return String.format("%1$s/%2$s/%3$s/%4$s/%5$s", dhpMessageType.getValue(),
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH), UUID.randomUUID().toString());
    }
}

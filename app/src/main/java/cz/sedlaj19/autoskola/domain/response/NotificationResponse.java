package cz.sedlaj19.autoskola.domain.response;

import java.util.List;

import cz.sedlaj19.autoskola.domain.model.Error;

/**
 * Created by Honza on 22. 1. 2017.
 */

public class NotificationResponse {

    private long multicast_id;
    private int success;
    private int failure;
    private long canonical_ids;
    private List<Error> results;

    public long getMulticast_id() {
        return multicast_id;
    }

    public boolean isSuccess() {
        return success == 1;
    }

    public boolean isFailure() {
        return failure == 1;
    }

    public long getCanonical_ids() {
        return canonical_ids;
    }

    public List<Error> getResults() {
        return results;
    }
}

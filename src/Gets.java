package src;

import com.couchbase.client.CouchbaseClient;

/*
 * Module that runs get operations
 */

public class Gets {
    public static void get_items (CouchbaseClient client, Variables V, String _prefix) {
        boolean checkFlag = true;
        int count, attempts = 0;
        if (V.getSetRatio() == 0.0) {
            count = V.getItemCount() + V.getAddCount();
        } else {
            count = (int)(((1.0 - V.getSetRatio()) *
                        (V.getItemCount() + V.getAddCount())) / V.getSetRatio());
        }
        while (checkFlag) {
            for (int i=0; i<(V.getItemCount() + V.getAddCount()); i++) {
                if (count == 0) {
                    System.out.println("All Gets Successful");
                    checkFlag = false;
                    break;
                }
                if (attempts == V.getItemCount() + V.getAddCount()) {
                    System.out.println("Not all Gets successful");
                    checkFlag = false;
                    break;
                }
                Object item = client.get(String.format("%s%d", _prefix, i));
                if (item != null) {
                    count --;
                }
                attempts++;
            }
        }
    }
}

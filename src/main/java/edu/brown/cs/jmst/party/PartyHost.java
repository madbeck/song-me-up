package edu.brown.cs.jmst.party;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

//NOT BEING USED. CONSIDERING FOR DELETION
public class PartyHost extends User {

  public PartyHost(User u) throws ClientProtocolException, IOException {
    assert u.isPremium();
    assert !u.inParty();
    this.logIn(u.getAuth(), u.getRefresh());
  }

}

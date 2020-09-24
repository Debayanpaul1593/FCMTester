package com.example.fcmtester;

/*
      Good Faith Statement & Confidentiality : The below code is part of IMPACTO Suite of products .
      Sirma Business Consulting India reserves all rights to this code . No part of this code should
      be copied, stored or transmitted in any form for whatsoever reason without prior written consent
      of Sirma Business Consulting (India).Employees or developers who have access to this code shall
      take all reasonable precautions to protect the source code and documentation, and preserve its
      confidential, proprietary and trade secret status in perpetuity.Any breach of the obligations
      to protect confidentiality of IMPACTO may cause immediate and irreparable harm to Sirma Business
      Consulting, which cannot be adequately compensated by monetary damages. Accordingly, any breach
      or threatened breach of confidentiality shall entitle Sirma Business Consulting to seek preliminary
      and permanent injunctive relief in addition to such remedies as may otherwise be available.

      //But by the grace of God We are what we are, and his grace to us was not without effect. No,
      //We worked harder than all of them--yet not We, but the grace of God that was with us.
      ----------------------------------------------------------------------------------------------
      |Version No  | Changed by | Date         | Change Tag  | Changes Done
      ----------------------------------------------------------------------------------------------
      |0.1 Beta    | Debayan     | Feb 2, 2018  | #DBP00001   | Initial writing
      ----------------------------------------------------------------------------------------------

*/public class User {

    private int id;
    private String name;
    private String email;

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}

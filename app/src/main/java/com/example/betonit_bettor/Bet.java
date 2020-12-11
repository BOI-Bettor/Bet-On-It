package com.example.betonit_bettor;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.Serializable;
import java.util.Date;

@ParseClassName("Bet")
public class Bet extends ParseObject {
    public static final String KEY_BET_NAME = "bet_Name";
    private static final String KEY_BET_AMOUNT = "bet_Amount";
    private static final String KEY_BET_STATUS = "bet_Status";
    private static final String KEY_BET_DESCRIPTION = "bet_Desc";
    private static final String KEY_BET_CHALLENGER = "bet_Challenger";
    private static final String KEY_BET_CHALLENGEE = "bet_Challengee";
    private static final String KEY_BET_START = "bet_Start";
    private static final String KEY_BET_END = "bet_End";
    private static final String KEY_BET_TYPE = "bet_Type";

    public String getBetName() {
        return getString(KEY_BET_NAME);
    }

    public void setBetName(String bet_Name)
    {
        put(KEY_BET_NAME, bet_Name);
    }

    public int getBetAmount() {
        return getInt(KEY_BET_AMOUNT);
    }

    public void setBetAmount(double bet_Amount)
    {
        put(KEY_BET_AMOUNT, bet_Amount);
    }

    public String getBetStatus() {
        return getString(KEY_BET_STATUS);
    }

    public void setBetStatus(String bet_Status)
    {
        put(KEY_BET_STATUS, bet_Status);
    }

    public String getBetDescription() {
        return getString(KEY_BET_DESCRIPTION);
    }

    public String getBetStart() { return getString(KEY_BET_START); }

    public void setBetStart(Date bet_Start) { put(KEY_BET_START, bet_Start);}

    public String getBetEnd() { return getString(KEY_BET_END); }

    public void setBetEnd(Date bet_End) { put(KEY_BET_END, bet_End);}

    public String getBetType() { return getString(KEY_BET_TYPE); }

    public void setBetType(String bet_Type) { put(KEY_BET_TYPE, bet_Type);}

    public void setBetDescription(String bet_Desc)
    {
        put(KEY_BET_DESCRIPTION, bet_Desc);
    }

    public ParseUser getBetChallenger()
    {
        return getParseUser(KEY_BET_CHALLENGER);
    }

    public void setBetChallenger(ParseUser challenger)
    {
        put(KEY_BET_CHALLENGER, challenger);
    }

    public ParseUser getBetChallengee()
    {
        return getParseUser(KEY_BET_CHALLENGEE);
    }

    public void setBetChallengee(ParseUser challengee)
    {
        put(KEY_BET_CHALLENGEE, challengee);
    }

}

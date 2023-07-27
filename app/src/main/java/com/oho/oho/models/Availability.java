package com.oho.oho.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Availability {
    @SerializedName("slot_1")
    @Expose
    private Integer slot1;
    @SerializedName("slot_2")
    @Expose
    private Integer slot2;
    @SerializedName("slot_3")
    @Expose
    private Integer slot3;
    @SerializedName("slot_4")
    @Expose
    private Integer slot4;
    @SerializedName("slot_5")
    @Expose
    private Integer slot5;
    @SerializedName("slot_6")
    @Expose
    private Integer slot6;
    @SerializedName("slot_7")
    @Expose
    private Integer slot7;
    @SerializedName("slot_8")
    @Expose
    private Integer slot8;
    @SerializedName("slot_9")
    @Expose
    private Integer slot9;
    @SerializedName("slot_10")
    @Expose
    private Integer slot10;
    @SerializedName("slot_11")
    @Expose
    private Integer slot11;

    public Integer getSlot1() {
        return slot1;
    }

    public void setSlot1(Integer slot1) {
        this.slot1 = slot1;
    }

    public Integer getSlot2() {
        return slot2;
    }

    public void setSlot2(Integer slot2) {
        this.slot2 = slot2;
    }

    public Integer getSlot3() {
        return slot3;
    }

    public void setSlot3(Integer slot3) {
        this.slot3 = slot3;
    }

    public Integer getSlot4() {
        return slot4;
    }

    public void setSlot4(Integer slot4) {
        this.slot4 = slot4;
    }

    public Integer getSlot5() {
        return slot5;
    }

    public void setSlot5(Integer slot5) {
        this.slot5 = slot5;
    }

    public Integer getSlot6() {
        return slot6;
    }

    public void setSlot6(Integer slot6) {
        this.slot6 = slot6;
    }

    public Integer getSlot7() {
        return slot7;
    }

    public void setSlot7(Integer slot7) {
        this.slot7 = slot7;
    }

    public Integer getSlot8() {
        return slot8;
    }

    public void setSlot8(Integer slot8) {
        this.slot8 = slot8;
    }

    public Integer getSlot9() {
        return slot9;
    }

    public void setSlot9(Integer slot9) {
        this.slot9 = slot9;
    }

    public Integer getSlot10() {
        return slot10;
    }

    public void setSlot10(Integer slot10) {
        this.slot10 = slot10;
    }

    public Integer getSlot11() {
        return slot11;
    }

    public void setSlot11(Integer slot11) {
        this.slot11 = slot11;
    }

    public Integer[] getAllSlotsAsArray() {
        ArrayList<Integer> allSlotsList = new ArrayList<>();

        // Add all the slots to the list
        allSlotsList.add(slot1);
        allSlotsList.add(slot2);
        allSlotsList.add(slot3);
        allSlotsList.add(slot4);
        allSlotsList.add(slot5);
        allSlotsList.add(slot6);
        allSlotsList.add(slot7);
        allSlotsList.add(slot8);
        allSlotsList.add(slot9);
        allSlotsList.add(slot10);
        allSlotsList.add(slot11);

        // Convert the list to an array and return it
        Integer[] allSlotsArray = allSlotsList.toArray(new Integer[0]);
        return allSlotsArray;
    }
}

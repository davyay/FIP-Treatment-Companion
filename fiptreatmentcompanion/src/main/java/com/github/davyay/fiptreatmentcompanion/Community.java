package com.github.davyay.fiptreatmentcompanion;

import java.util.ArrayList;
import java.util.List;

public class Community {
    // List of pre-defined links
    private List<String> links;

    public Community() {
        links = new ArrayList<>();
        initializeLinks();
    }

    // Private method to add all initial links
    private void initializeLinks() {
        links.add("https://fipwarriors.com/ - FIP Warrors Support Group");
        links.add("https://www.facebook.com/groups/fipwarriorsoriginal/ - FIP Warriors Facebook Group");
    }

    // Public method to get the links
    public List<String> getLinks() {
        return new ArrayList<>(links); 
    }

    // Convert all links to a single string for display (WIP)
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Community Support Links:\n");
        for (String link : links) {
            sb.append(link).append("\n");
        }
        return sb.toString();
    }
}

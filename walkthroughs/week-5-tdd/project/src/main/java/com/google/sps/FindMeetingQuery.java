// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    // throw new UnsupportedOperationException("TODO: Implement this method.");
    HashSet<String> attendees = new HashSet<String>(request.getAttendees());
    HashSet<String> optionalAttendees = new HashSet<String>(request.getOptionalAttendees());
    ArrayList<TimeRange> badTimes = new ArrayList<TimeRange>();
    ArrayList<TimeRange> optionalBad = new ArrayList<TimeRange>();
    Collection<TimeRange> range = new ArrayList<TimeRange>();
    Collection<TimeRange> OptRange = new ArrayList<TimeRange>();
    long meetingDuration = request.getDuration();
    int duration = (int) meetingDuration;

    //Base Cases
    if(request.getAttendees().size() < 1){
        return Arrays.asList(TimeRange.WHOLE_DAY);
    }

    if(meetingDuration > 1440){  // 1440 is the timeRange whole day in minutes
        return Arrays.asList();
    }

    if(events.size()<1){
        return Arrays.asList(TimeRange.WHOLE_DAY);
    }

    //Put all of the bad times in list   : if it is related to an attendee
    for (Event event : events) {
        int eventStart = event.getWhen().start();
        int eventDuration = event.getWhen().duration();
        Iterator<String> i = event.getAttendees().iterator();  
        while(i.hasNext()) {  
           String a = i.next();
           if(attendees.contains(a)){
            badTimes.add(TimeRange.fromStartDuration(eventStart,eventDuration));
           }
           else if(optionalAttendees.contains(a)){
            optionalBad.add(TimeRange.fromStartDuration(eventStart,eventDuration));
           }
        }  
    }
    
    if(badTimes.size()<1){
        return Arrays.asList(TimeRange.WHOLE_DAY);
    }

    // Sort unavailable times list
    Collections.sort(badTimes, TimeRange.ORDER_BY_START);
    ArrayList<TimeRange> finalUnavailable = new ArrayList<TimeRange>();
    boolean lastAdded = false;
   
    //Combine unavailable times if they overlap
    for(int i = 1; i< badTimes.size();i++){
        if(badTimes.get(i-1).overlaps(badTimes.get(i))) {
            lastAdded=true;
            int s = badTimes.get(i-1).start();
            int e = badTimes.get(i-1).end();
            if (badTimes.get(i).start() < s){
                s = badTimes.get(i).start();
            }
            if (badTimes.get(i).end() > e){
                e = badTimes.get(i).end();
            }
            finalUnavailable.add(TimeRange.fromStartEnd(s,e,false));
        } else {
            lastAdded=false;
            finalUnavailable.add(badTimes.get(i-1));
        }
    }
    if(!lastAdded){
        finalUnavailable.add(badTimes.get(badTimes.size()-1));
    }

    int timeStart = 0;
    //Finally Make a list of gaps in unavailable times that have at least a duration of the Meeting's Duration
    for(TimeRange timeRange : finalUnavailable){
        int badStart = timeRange.start();
        int badDuration = timeRange.duration();
        int badEnd = timeRange.end();
        int newTimeStart = timeStart + duration;
        if(newTimeStart > TimeRange.END_OF_DAY){
            break;
        }   
        if(badStart >= timeStart + duration){
            range.add(TimeRange.fromStartEnd(timeStart,badStart,false));
        }
        timeStart = badEnd;
    }

    //optional attendees
    // For each optional attendee check to see if they fit in schedule
    //or check Optional times seperate and see if there is an overlap 
    //check optional good times and see if it overlaps with other good times
    // If yes make new list with just those good times
    
    // Extra: see which optional has the most time 
    //Make Optional Range
    //minimize time based on optional ranges by getting the overlapping times

    //After loop check if the rest of the day fits in duration
    if (timeStart+duration < TimeRange.END_OF_DAY){
        range.add(TimeRange.fromStartEnd(timeStart,TimeRange.END_OF_DAY,true));
    }

    return range;
  }
}

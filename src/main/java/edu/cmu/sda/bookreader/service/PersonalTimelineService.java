package edu.cmu.sda.bookreader.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Scope(value = "session")
@Component(value = "personalTimelineService")
public class PersonalTimelineService extends AbstractTimelineService{

    public PersonalTimelineService(){super();}

}
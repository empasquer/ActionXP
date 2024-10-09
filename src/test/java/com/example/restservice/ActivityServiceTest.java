package com.example.restservice;

import com.example.restservice.models.Activity;
import com.example.restservice.services.ActivityService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Use MockitoExtension to enable Mockito annotations
public class ActivityServiceTest {

    @Mock
    private ObjectMapper objectMapper; // Mock ObjectMapper

    @InjectMocks
    private ActivityService activityService; // Inject mocks into ActivityService

    @BeforeEach
    void setup() {
        // No additional initialization needed as we're using @InjectMocks
    }

    @Test
    void testSaveActivity() throws IOException {
        Activity activity = new Activity();
        activity.setActivityName("test");
        activity.setDurations(List.of("10", "20", "30"));
        activity.setInformation("This is a test sample");

        // Mock the readValue method to return an empty list
        when(objectMapper.readValue(any(File.class), any(TypeReference.class)))
                .thenReturn(new ArrayList<>());

        // Mock the writeValue method to do nothing
        doNothing().when(objectMapper).writeValue(any(File.class), anyList());

        // Call the method under test
        activityService.saveActivity(activity, "src/main/java/com/example/restservice/resources/activities.json");

        // Verify that writeValue was called with any File and any List
        verify(objectMapper).writeValue(any(File.class), anyList());
    }
}

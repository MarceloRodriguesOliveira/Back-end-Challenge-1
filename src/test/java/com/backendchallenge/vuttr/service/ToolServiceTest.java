package com.backendchallenge.vuttr.service;

import com.backendchallenge.vuttr.controller.dto.createToolDto;
import com.backendchallenge.vuttr.entity.Tool;
import com.backendchallenge.vuttr.repository.ToolRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ToolServiceTest {

    @Mock
    ToolRepository toolRepository;

    @InjectMocks
    private ToolService toolService;

    @Mock
    private ArrayList<String> mockArrayList;


    @Captor
    private ArgumentCaptor<Tool> toolArgumentCaptor;

    @Captor
    private ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);

    @Captor
    private ArgumentCaptor<String> tagsArgumentCaptor = ArgumentCaptor.forClass(String.class);

    @Nested
    class findAll{
        @Test
        @DisplayName("Should return all tools registered")
        void shouldReturnAllTools(){
            // Arrange
            var tool = new Tool(
                    "generatedId",
                    "tool",
                    "http//something.com",
                    "description",
                    mockArrayList
            );

            var toolsList = List.of(tool);
            doReturn(toolsList).when(toolRepository).findAll();

            // Act
            var output = toolService.findAll();

            // Assert
            assertNotNull(output);
            assertEquals(toolsList.size(), output.size());
        }
    }

    @Nested
    class createNewTool{
        @Test
        @DisplayName("Should Create a new Tool and insert into database")
        void shouldCreateNewTool(){
            // Arrange
            var tool = new Tool(
                    "generatedId",
                    "tool",
                    "http//something.com",
                    "description",
                    mockArrayList
            );
            doReturn(tool).when(toolRepository).save(toolArgumentCaptor.capture());
            // Act
            var output = toolService.save(tool);

            //Assert
            assertNotNull(output);
            var capturedTool = toolArgumentCaptor.getValue();
            assertEquals(output.getTitle(), capturedTool.getTitle());
            assertEquals(output.getLink(), capturedTool.getLink());
            assertEquals(output.getDescription(), capturedTool.getDescription());
            assertEquals(output.getTags().size(), capturedTool.getTags().size());
        }

        @Test
        @DisplayName("Should Throw Exception When Error Occurs")
        void shouldThrowExceptionWhenErrorOccurs(){
            // Arrange
            doThrow(new RuntimeException()).when(toolRepository).save(any());
            var input = new Tool(
                    "generatedId",
                    "tool",
                    "http//something.com",
                    "description",
                    mockArrayList
            );

            //Act 7 Assert
            assertThrows(RuntimeException.class, ()-> toolService.save(input));

        }
    }

    @Nested
    class deleteById{
        @Test
        @DisplayName("Should Delete By Id if Id exists")
        void shouldDeleteByIfExists(){
            doReturn(true)
                    .when(toolRepository)
                    .existsById(idCaptor.capture());

            doNothing()
                    .when(toolRepository)
                    .deleteById(idCaptor.capture());

            var toolId = "unique-id";

            toolService.deleteById(toolId);

            var idList = idCaptor.getAllValues();
            assertEquals(toolId, idList.get(0));
            assertEquals(toolId, idList.get(1));


            verify(toolRepository, times(1)).existsById(toolId);
            verify(toolRepository, times(1)).deleteById(toolId);
        }

        @Test
        @DisplayName("Should not delete tool if does not exists")
        void shouldNotDeleteToolWhenToolDoesNotExist(){
            doReturn(false)
                    .when(toolRepository)
                    .existsById(idCaptor.capture());
            var toolId = "this id does not exist";

            toolService.deleteById(toolId);

            assertEquals(toolId, idCaptor.getValue());

            verify(toolRepository, times(1)).existsById(idCaptor.getValue());
            verify(toolRepository, times(0)).deleteById(any());
        }
    }

    @Nested
    class findByTagContaining{
        @Test
        @DisplayName("Should return tools that contain certain value for tag")
        void shouldReturnAllToolsThatContainCertainTagValue(){
            var tool = new Tool(
                    "generatedId",
                    "tool",
                    "http//something.com",
                    "description",
                    mockArrayList
            );

            var toolList = List.of(tool);
            doReturn(toolList)
                    .when(toolRepository)
                    .findByTagsContaining(tagsArgumentCaptor.capture());
            var tag = "tech tag";

            toolService.findByTags(tag);

            assertEquals(tag, tagsArgumentCaptor.getValue());

        }
    }

}
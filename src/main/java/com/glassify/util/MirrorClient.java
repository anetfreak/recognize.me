/*
 * Copyright (C) 2013 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.glassify.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.glassify.domain.MyCredential;
import com.glassify.facade.CredentialFacade;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.mirror.Mirror;
import com.google.api.services.mirror.model.Attachment;
import com.google.api.services.mirror.model.MenuItem;
import com.google.api.services.mirror.model.NotificationConfig;
import com.google.api.services.mirror.model.TimelineItem;
import com.google.common.io.ByteStreams;

/**
 * A facade for easier access to basic API operations
 *
 * @author Amit Agrawal
 */
public class MirrorClient {
	private static Logger logger = Logger.getLogger(MirrorClient.class.getSimpleName());
	
  //Main for testing
	public static void main(String[] args){
	// Send welcome timeline item
    TimelineItem timelineItem = new TimelineItem();
    timelineItem.setText("Welcome to the Glass Java Quick Start");
    timelineItem.setNotification(new NotificationConfig().setLevel("DEFAULT"));
    Credential credential = null; //TODO call db for getting credential
    TimelineItem insertedItem = null;
	try {
		MirrorClient mc = new MirrorClient();
		insertedItem = mc.insertTimelineItem(credential, timelineItem);
	} catch (IOException e) {
		e.printStackTrace();
	}
  }
  
  private static List<MenuItem> getMenuItems() {
	  List<MenuItem> menuItems = new ArrayList<MenuItem>();
	  MenuItem delOption = new MenuItem().setAction("DELETE");
	  MenuItem readOption = new MenuItem().setAction("READ_ALOUD");
	  menuItems.add(delOption);
	  menuItems.add(readOption);
	  return menuItems;
  }
  
  public TimelineItem createTimeLineItemWithText(String text){
	    TimelineItem timelineItem = new TimelineItem();
	    String decoratedText = "{" +
	    	  "'html': '<article><section><p class=\'text-auto-size\'><strong class=\'blue\'>AdFinder</strong><br/>"+
	    		text +
	    		"</p></section></article>',"+
	    		"'notification': {" +
	    		"'level': 'DEFAULT'" +
	    		"}"+
	    	"}";
	    		
	    timelineItem.setHtml(decoratedText);
	    timelineItem.setText(text);
	    timelineItem.setNotification(new NotificationConfig().setLevel("DEFAULT"));
	    timelineItem.setMenuItems(getMenuItems());    
	    return timelineItem;
	  }
  
  public Mirror getMirror(final Credential credential) {
    return new Mirror.Builder(new NetHttpTransport(), new JacksonFactory(), credential).setApplicationName("Recognize me").build();
  }

  /**
   * Inserts a simple timeline item.
   *
   * @param credential the user's credential
   * @param item       the item to insert
   */
  public TimelineItem insertTimelineItem(Credential credential, TimelineItem item)
      throws IOException {
    return getMirror(credential).timeline().insert(item).execute();
  }

  /**
   * Inserts an item with an attachment provided as a byte array.
   *
   * @param credential            the user's credential
   * @param item                  the item to insert
   * @param attachmentContentType the MIME type of the attachment (or null if
   *                              none)
   * @param attachmentData        data for the attachment (or null if none)
   */
  public void insertTimelineItem(Credential credential, TimelineItem item,
      String attachmentContentType, byte[] attachmentData) throws IOException {
    Mirror.Timeline timeline = getMirror(credential).timeline();
    timeline.insert(item, new ByteArrayContent(attachmentContentType, attachmentData)).execute();

  }

  /**
   * Inserts an item with an attachment provided as an input stream.
   *
   * @param credential            the user's credential
   * @param item                  the item to insert
   * @param attachmentContentType the MIME type of the attachment (or null if
   *                              none)
   * @param attachmentInputStream input stream for the attachment (or null if
   *                              none)
   */
  public void insertTimelineItem(final Credential credential, TimelineItem item,
      String attachmentContentType, InputStream attachmentInputStream) throws IOException {
    insertTimelineItem(credential, item, attachmentContentType,
        ByteStreams.toByteArray(attachmentInputStream));
  }

  public InputStream getAttachmentInputStream(Credential credential, String timelineItemId,
      String attachmentId) throws IOException {
    Mirror mirrorService = getMirror(credential);
    Mirror.Timeline.Attachments attachments = mirrorService.timeline().attachments();
    Attachment attachmentMetadata = attachments.get(timelineItemId, attachmentId).execute();
    HttpResponse resp =
        mirrorService.getRequestFactory()
            .buildGetRequest(new GenericUrl(attachmentMetadata.getContentUrl())).execute();
    return resp.getContent();
  }

  public String getAttachmentContentType(Credential credential, String timelineItemId,
      String attachmentId) throws IOException {
    Mirror.Timeline.Attachments attachments = getMirror(credential).timeline().attachments();
    Attachment attachmentMetadata = attachments.get(timelineItemId, attachmentId).execute();
    return attachmentMetadata.getContentType();
  }

  public void deleteTimelineItem(Credential credential, String timelineItemId) throws IOException {
    getMirror(credential).timeline().delete(timelineItemId).execute();    
  }
  
  
  public void testTimelineItem(CredentialFacade credentialFacade) {
	  TimelineItem timelineItem = new TimelineItem();
	    timelineItem.setNotification(new NotificationConfig().setLevel("DEFAULT"));
	    MyCredential credential = credentialFacade.getCredentialForUser("glass4sjsu@gmail.com");
		try {
			File file = new File("//Users//ameya//Downloads//apple.jpg");
			InputStream stream = new FileInputStream(file);
			String text = "Unfortunately, we could not identify any brand. Please take a clear and closer picture again.";
			String decoratedText = "{" +
			    	  "'html': '<article><section><p class=\'text-auto-size\'><strong class=\'blue\'>AdFinder</strong><br/>"+
			    		text +
			    		"</p></section></article>',"+
			    		"'notification': {" +
			    		"'level': 'DEFAULT'" +
			    		"}"+
			    	"}";
			TimelineItem item = insertTimelineItem(getMirror(credential.getCredential()), decoratedText, "image/jpeg", stream, "DEFAULT");
			
//			File file = new File("//Users//ameya//Downloads//doll.mp4");
//			InputStream stream = new FileInputStream(file);
//			TimelineItem item = insertTimelineItem(getMirror(credential.getCredential()), "Hola", "video/mp4", stream, "DEFAULT");
		} catch (IOException e) {
			e.printStackTrace();
		}
  }
  
  	public static TimelineItem insertTimelineItem(Mirror service, String text, String contentType,
	      InputStream attachment, String notificationLevel) {
	    TimelineItem timelineItem = new TimelineItem();
	    timelineItem.setHtml(text);
	    
	    timelineItem.setMenuItems(getMenuItems()); 
	    if (notificationLevel != null && notificationLevel.length() > 0) {
	      timelineItem.setNotification(new NotificationConfig().setLevel(notificationLevel));
	    }
	    try {
	      if (contentType != null && contentType.length() > 0 && attachment != null) {
	        // Insert both metadata and attachment.
	        InputStreamContent mediaContent = new InputStreamContent(contentType, attachment);
	        return service.timeline().insert(timelineItem, mediaContent).execute();
	      } else {
	        // Insert metadata only.
	        return service.timeline().insert(timelineItem).execute();
	      }
	    } catch (IOException e) {
	      System.err.println("An error occurred: " + e);
	      return null;
	    }
	  }
}

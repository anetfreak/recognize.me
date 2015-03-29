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
package com.glassify.adserver.Util;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.mirror.Mirror;
import com.google.api.services.mirror.model.Attachment;
import com.google.api.services.mirror.model.NotificationConfig;
import com.google.api.services.mirror.model.TimelineItem;
import com.google.common.io.ByteStreams;

/**
 * A facade for easier access to basic API operations
 *
 * @author Amit Agrawal
 */
public class MirrorClient {
  //private static final Logger LOG = Logger.getLogger(MirrorClient.class.getSimpleName());

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
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    //LOG.info("Bootstrapper inserted welcome message " + insertedItem.getId() + " for user "
      //  + "test");
  }
  
  
  public Mirror getMirror(Credential credential) {
    return new Mirror.Builder(new NetHttpTransport(), new JacksonFactory(), credential)
        .setApplicationName("Glass AdServer").build();
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
  public void insertTimelineItem(Credential credential, TimelineItem item,
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
}

/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.liwy.easymusic.common.http.converter;

import android.util.Xml;

import com.orhanobut.logger.Logger;

import org.simpleframework.xml.Serializer;
import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class EcrfResponseBodyConverter<T> implements Converter<ResponseBody, T> {
  private final Class<T> cls;
  private final Serializer serializer;
  private final boolean strict;

  EcrfResponseBodyConverter(Class<T> cls, Serializer serializer, boolean strict) {
    this.cls = cls;
    this.serializer = serializer;
    this.strict = strict;
  }

  @Override public T convert(ResponseBody value) throws IOException {
    try {
      InputStream contentStream = convertToEcrfContent(value.byteStream());
      Reader reader = new InputStreamReader(contentStream, "UTF-8");
      T read = serializer.read(cls, reader, strict);
      if (read == null) {
        throw new IllegalStateException("Could not deserialize body as " + cls);
      }
      return read;
    } catch (RuntimeException | IOException e) {
      throw e;
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      value.close();
    }
  }
  //WebService返回的结果
  public InputStream convertToEcrfContent(InputStream xmlIs) {
    String mResponseBody = "";
    try {
      XmlPullParser xmlPullParser = Xml.newPullParser();
      xmlPullParser.setInput(xmlIs, "UTF-8");
      int eventType = xmlPullParser.getEventType();
      int i = 0;
      while (eventType != XmlPullParser.END_DOCUMENT) {
        // 实体字符没有转换所以只起到去掉头部作用
        switch (eventType) {
          case XmlPullParser.START_DOCUMENT:
            break;
          case XmlPullParser.START_TAG:
            mResponseBody = xmlPullParser.nextText();
            break;
          default:
            break;
        }
        eventType = xmlPullParser.next();
      }

      Logger.d("修正后服务端返回数据:",mResponseBody);
      xmlIs = new ByteArrayInputStream(mResponseBody.getBytes("UTF-8"));
      return xmlIs;
    } catch (UnsupportedEncodingException exp) {
      throw new RuntimeException("解析数据过程出现错误");
    } catch (RuntimeException exp) {
      throw exp;
    } catch (Exception exp) {
      throw new RuntimeException(exp.toString());
    }
  }
}

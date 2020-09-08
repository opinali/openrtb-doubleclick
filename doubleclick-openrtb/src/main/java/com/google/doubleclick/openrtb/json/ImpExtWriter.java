/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.doubleclick.openrtb.json;

import static com.google.openrtb.json.OpenRtbJsonUtils.writeEnumField;
import static com.google.openrtb.json.OpenRtbJsonUtils.writeIntBoolField;
import static com.google.openrtb.json.OpenRtbJsonUtils.writeInts;
import static com.google.openrtb.json.OpenRtbJsonUtils.writeLongs;
import static com.google.openrtb.json.OpenRtbJsonUtils.writeStrings;

import com.fasterxml.jackson.core.JsonGenerator;
import com.google.doubleclick.AdxExt.ImpExt;
import com.google.doubleclick.AdxExt.ImpExt.BuyerGeneratedRequestData;
import com.google.doubleclick.AdxExt.ImpExt.BuyerGeneratedRequestData.SourceApp;
import com.google.doubleclick.AdxExt.ImpExt.ExcludedCreative;
import com.google.doubleclick.AdxExt.ImpExt.OpenBidding;
import com.google.doubleclick.AdxExt.ImpExt.OpenBidding.AdUnitMapping;
import com.google.doubleclick.AdxExt.ImpExt.OpenBidding.AdUnitMapping.Keyval;
import com.google.openrtb.json.OpenRtbJsonExtWriter;
import java.io.IOException;

/** Writer for {@link ImpExt}. */
class ImpExtWriter extends OpenRtbJsonExtWriter<ImpExt> {

  @Override
  protected void write(ImpExt ext, JsonGenerator gen) throws IOException {
    writeLongs("billing_id", ext.getBillingIdList(), gen);
    writeLongs("publisher_settings_list_id", ext.getPublisherSettingsListIdList(), gen);
    writeInts("allowed_vendor_type", ext.getAllowedVendorTypeList(), gen);
    writeStrings("publisher_parameter", ext.getPublisherParameterList(), gen);
    if (ext.hasDfpAdUnitCode()) {
      gen.writeStringField("dfp_ad_unit_code", ext.getDfpAdUnitCode());
    }
    if (ext.hasIsRewardedInventory()) {
      writeIntBoolField("is_rewarded_inventory", ext.getIsRewardedInventory(), gen);
    }
    if (ext.hasAmpad()) {
      writeEnumField("ampad", ext.getAmpad(), gen);
    }
    if (ext.getBuyerGeneratedRequestDataCount() != 0) {
      gen.writeArrayFieldStart("buyer_generated_request_data");
      for (BuyerGeneratedRequestData feedback : ext.getBuyerGeneratedRequestDataList()) {
        writeBuyerGeneratedRequestData(feedback, gen);
      }
      gen.writeEndArray();
    }
    if (ext.getExcludedCreativesCount() != 0) {
      gen.writeArrayFieldStart("excluded_creatives");
      for (ExcludedCreative exCreat : ext.getExcludedCreativesList()) {
        writeExcludedCreative(exCreat, gen);
      }
      gen.writeEndArray();
    }
    if (ext.hasOpenBidding()) {
      gen.writeFieldName("open_bidding");
      writeOpenBidding(ext.getOpenBidding(), gen);
    }
    writeInts("allowed_restricted_category", ext.getAllowedRestrictedCategoryList(), gen);
  }

  public final void writeOpenBidding(OpenBidding obid, JsonGenerator gen) throws IOException {
    gen.writeStartObject();
    writeOpenBiddingFields(obid, gen);
    gen.writeEndObject();
    gen.flush();
  }

  protected void writeOpenBiddingFields(OpenBidding obid, JsonGenerator gen) throws IOException {
    if (obid.hasIsOpenBidding()) {
      writeIntBoolField("is_open_bidding", obid.getIsOpenBidding(), gen);
    }
    if (obid.getAdunitMappingsCount() != 0) {
      gen.writeArrayFieldStart("adunit_mappings");
      for (AdUnitMapping aum : obid.getAdunitMappingsList()) {
        writeAdUnitMapping(aum, gen);
      }
      gen.writeEndArray();
    }
  }

  public final void writeAdUnitMapping(AdUnitMapping aum, JsonGenerator gen) throws IOException {
    gen.writeStartObject();
    writeAdUnitMappingFields(aum, gen);
    gen.writeEndObject();
    gen.flush();
  }

  protected void writeAdUnitMappingFields(AdUnitMapping aum, JsonGenerator gen) throws IOException {
    if (aum.hasFormat()) {
      writeEnumField("format", aum.getFormat(), gen);
    }
    if (aum.getKeyvalsCount() != 0) {
      gen.writeArrayFieldStart("keyvals");
      for (Keyval kv : aum.getKeyvalsList()) {
        writeKeyVal(kv, gen);
      }
      gen.writeEndArray();
    }
  }

  public final void writeKeyVal(Keyval kv, JsonGenerator gen) throws IOException {
    gen.writeStartObject();
    writeKeyvalFields(kv, gen);
    gen.writeEndObject();
    gen.flush();
  }

  protected void writeKeyvalFields(Keyval kv, JsonGenerator gen) throws IOException {
    if (kv.hasKey()) {
      gen.writeStringField("key", kv.getKey());
    }
    if (kv.hasValue()) {
      gen.writeStringField("value", kv.getValue());
    }
  }

  public final void writeBuyerGeneratedRequestData(
      BuyerGeneratedRequestData req, JsonGenerator gen) throws IOException {
    gen.writeStartObject();
    writeBuyerGeneratedRequestDataFields(req, gen);
    gen.writeEndObject();
    gen.flush();
  }

  protected void writeBuyerGeneratedRequestDataFields(
      BuyerGeneratedRequestData req, JsonGenerator gen) throws IOException {
    switch (req.getSourceCase()) {
      case SOURCE_APP:
        gen.writeFieldName("source_app");
        writeSourceApp(req.getSourceApp(), gen);
        break;
      default:
    }
    if (req.hasData()) {
      gen.writeStringField("data", req.getData());
    }
  }

  public final void writeSourceApp(SourceApp app, JsonGenerator gen) throws IOException {
    gen.writeStartObject();
    writeSourceAppFields(app, gen);
    gen.writeEndObject();
    gen.flush();
  }

  protected void writeSourceAppFields(SourceApp app, JsonGenerator gen) throws IOException {
    if (app.hasId()) {
      gen.writeStringField("id", app.getId());
    }
  }

  public final void writeExcludedCreative(ExcludedCreative exCreat, JsonGenerator gen)
      throws IOException {
    gen.writeStartObject();
    writeExcludedCreativeFields(exCreat, gen);
    gen.writeEndObject();
    gen.flush();
  }

  protected void writeExcludedCreativeFields(ExcludedCreative exCreat, JsonGenerator gen)
      throws IOException {
    if (exCreat.hasBuyerCreativeId()) {
      gen.writeStringField("buyer_creative_id", exCreat.getBuyerCreativeId());
    }
  }
}

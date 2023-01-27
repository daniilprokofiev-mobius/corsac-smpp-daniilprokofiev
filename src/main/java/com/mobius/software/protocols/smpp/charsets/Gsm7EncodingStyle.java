package com.mobius.software.protocols.smpp.charsets;
/*
 * Mobius Software LTD
 * Copyright 2019 - 2023, Mobius Software LTD and individual contributors
 * by the @authors tag.
 *
 * This program is free software: you can redistribute it and/or modify
 * under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
public enum Gsm7EncodingStyle {
    /**
     * 7-bit packing - SMS
     */
    bit7_sms_style,
    /**
     * 7-bit packing - USSD
     */
    bit7_ussd_style,
    /**
     * 8-bit packing - SMPP
     */
    bit8_smpp_style,
}

/*
 * Copyright 2009 Google Inc.
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

package uk.ac.ncl.openlab.intake24.client.api.auth;

import uk.ac.ncl.openlab.intake24.client.BrowserConsole;

/**
 * A utility to decode and encode byte arrays as Strings, using only "safe"
 * characters.
 */
public class Base64Utils {

    /**
     * An array mapping size but values to the characters that will be used to
     * represent them. Note that this is not identical to the set of characters
     * used by MIME-Base64.
     */
    private static final char[] base64Chars = new char[]{
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b',
            'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
            'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '$', '_'};

    /**
     * An array mapping legal base 64 characters [a-zA-Z0-9$_] to their associated
     * 6-bit values. The source indices will be given by 7-bit ASCII characters,
     * thus the array size needs to be 128 (actually 123 would suffice for the
     * given set of characters in use).
     */
    private static final byte[] base64Values = new byte[128];

    /**
     * Initialize the base 64 encoder values.
     */
    static {
        // Invert the mapping (i -> base64Chars[i])
        for (int i = 0; i < base64Chars.length; i++) {
            base64Values[base64Chars[i]] = (byte) i;
        }
    }

    /**
     * Decode a base64 string into a byte array.
     *
     * @param data the encoded data.
     * @return a byte array.
     * @see #fromBase64(String)
     */
    public static byte[] fromBase64(String data) {
        if (data == null) {
            return null;
        }

        int len = data.length();
        if (len % 4 != 0)
            throw new RuntimeException("Invalid base64 string length (must be a multiple of 4");

        if (len == 0) {
            return new byte[0];
        }

        char[] chars = new char[len];
        data.getChars(0, len, chars, 0);

        int olen = 3 * (len / 4);
        if (chars[len - 2] == '=') {
            --olen;
        }
        if (chars[len - 1] == '=') {
            --olen;
        }

        byte[] bytes = new byte[olen];

        int iidx = 0;
        int oidx = 0;
        while (iidx < len) {
            int c0 = base64Values[chars[iidx++] & 0xff];
            int c1 = base64Values[chars[iidx++] & 0xff];
            int c2 = base64Values[chars[iidx++] & 0xff];
            int c3 = base64Values[chars[iidx++] & 0xff];
            int c24 = (c0 << 18) | (c1 << 12) | (c2 << 6) | c3;

            bytes[oidx++] = (byte) (c24 >> 16);
            if (oidx == olen) {
                break;
            }
            bytes[oidx++] = (byte) (c24 >> 8);
            if (oidx == olen) {
                break;
            }
            bytes[oidx++] = (byte) c24;
        }

        return bytes;
    }

    public static byte[] fromBase64Url(String data) {
        String base64Data = data.replace('-', '+').replace('_', '/');

        switch (base64Data.length() % 4) {
            case 0:
                break;
            case 2:
                base64Data += "==";
                break;
            case 3:
                base64Data += "=";
                break;
            default:
                throw new RuntimeException("Invalid base64url string length (must be even)");
        }

        return fromBase64(base64Data);
    }

}
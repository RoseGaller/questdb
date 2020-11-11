/*******************************************************************************
 *    ___                  _   ____  ____
 *   / _ \ _   _  ___  ___| |_|  _ \| __ )
 *  | | | | | | |/ _ \/ __| __| | | |  _ \
 *  | |_| | |_| |  __/\__ \ |_| |_| | |_) |
 *   \__\_\\__,_|\___||___/\__|____/|____/
 *
 * Copyright (C) 2014-2016 Appsicle
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 ******************************************************************************/

package io.questdb.griffin.engine.analytic;

import io.questdb.cairo.TableColumnMetadata;
import io.questdb.cairo.sql.Record;
import io.questdb.cairo.sql.RecordCursor;
import io.questdb.cairo.sql.SymbolTable;
import io.questdb.std.str.CharSink;

public interface AnalyticFunction {
    int STREAM = 1;
    int TWO_PASS = 2;
    int THREE_PASS = 3;

    void add(Record record);

    byte get();

    boolean getBool();

    long getDate();

    double getDouble();

    float getFloat();

    CharSequence getStr();

    CharSequence getStrB();

    int getInt();

    long getLong();

    TableColumnMetadata getMetadata();

    short getShort();

    void getStr(CharSink sink);

    int getStrLen();

    String getSym();

    SymbolTable getSymbolTable();

    int getType();

    void prepare(RecordCursor cursor);

    void prepareFor(Record record);

    void reset();

    void toTop();
}

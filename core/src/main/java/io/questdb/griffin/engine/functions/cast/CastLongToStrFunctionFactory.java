/*******************************************************************************
 *     ___                  _   ____  ____
 *    / _ \ _   _  ___  ___| |_|  _ \| __ )
 *   | | | | | | |/ _ \/ __| __| | | |  _ \
 *   | |_| | |_| |  __/\__ \ |_| |_| | |_) |
 *    \__\_\\__,_|\___||___/\__|____/|____/
 *
 *  Copyright (c) 2014-2019 Appsicle
 *  Copyright (c) 2019-2020 QuestDB
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 ******************************************************************************/

package io.questdb.griffin.engine.functions.cast;

import io.questdb.cairo.CairoConfiguration;
import io.questdb.cairo.sql.Function;
import io.questdb.cairo.sql.Record;
import io.questdb.griffin.FunctionFactory;
import io.questdb.griffin.engine.functions.StrFunction;
import io.questdb.griffin.engine.functions.UnaryFunction;
import io.questdb.griffin.engine.functions.constants.StrConstant;
import io.questdb.std.Chars;
import io.questdb.std.Misc;
import io.questdb.std.Numbers;
import io.questdb.std.ObjList;
import io.questdb.std.str.CharSink;
import io.questdb.std.str.StringSink;

public class CastLongToStrFunctionFactory implements FunctionFactory {
    @Override
    public String getSignature() {
        return "cast(Ls)";
    }

    @Override
    public Function newInstance(ObjList<Function> args, int position, CairoConfiguration configuration) {
        Function func = args.getQuick(0);
        if (func.isConstant()) {
            StringSink sink = Misc.getThreadLocalBuilder();
            Numbers.append(sink, func.getLong(null));
            return new StrConstant(position, Chars.toString(sink));
        }
        return new Func(position, args.getQuick(0));
    }

    private static class Func extends StrFunction implements UnaryFunction {
        private final Function arg;
        private final StringSink sinkA = new StringSink();
        private final StringSink sinkB = new StringSink();

        public Func(int position, Function arg) {
            super(position);
            this.arg = arg;
        }

        @Override
        public Function getArg() {
            return arg;
        }

        @Override
        public CharSequence getStr(Record rec) {
            final long value = arg.getLong(rec);
            if (value == Numbers.LONG_NaN) {
                return null;
            }
            sinkA.clear();
            Numbers.append(sinkA, value);
            return sinkA;
        }

        @Override
        public CharSequence getStrB(Record rec) {
            final long value = arg.getLong(rec);
            if (value == Numbers.LONG_NaN) {
                return null;
            }
            sinkB.clear();
            Numbers.append(sinkB, value);
            return sinkB;
        }

        @Override
        public void getStr(Record rec, CharSink sink) {
            final long value = arg.getLong(rec);
            if (value == Numbers.LONG_NaN) {
                return;
            }

            Numbers.append(sink, value);
        }
    }
}
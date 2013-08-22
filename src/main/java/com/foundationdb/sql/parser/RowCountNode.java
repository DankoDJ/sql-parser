/**
 * Copyright 2011-2013 FoundationDB, LLC
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

/* The original from which this derives bore the following: */

/*

   Derby - Class org.apache.derby.impl.sql.compile.RowCountNode

   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to you under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

*/

package com.foundationdb.sql.parser;

import com.foundationdb.sql.StandardException;

/**
 * The result set generated by this node (RowCountResultSet) implements the
 * filtering of rows needed for the <result offset clause> and the <fetch first
 * clause>.  It sits on top of the normal SELECT's top result set, but under any
 * ScrollInsensitiveResultSet. The latter's positioning is needed for the correct
 * functioning of <result offset clause> and <fetch first clause> in the
 * presence of scrollable and/or updatable result sets and CURRENT OF cursors.
 */
public final class RowCountNode extends SingleChildResultSetNode
{
    /**
     * If not null, this represents the value of a <result offset clause>.
     */
    private ValueNode offset;
    /**
     * If not null, this represents the value of a <fetch first clause>.
     */
    private ValueNode fetchFirst;

    /**
     * Initializer for a RowCountNode
     *
     * @exception StandardException
     */
    public void init(Object childResult,
                     Object rcl,
                     Object offset,
                     Object fetchFirst)
            throws StandardException {

        init(childResult, null);
        resultColumns = (ResultColumnList)rcl;

        this.offset = (ValueNode)offset;
        this.fetchFirst = (ValueNode)fetchFirst;
    }

    /**
     * Fill this node with a deep copy of the given node.
     */
    public void copyFrom(QueryTreeNode node) throws StandardException {
        super.copyFrom(node);

        RowCountNode other = (RowCountNode)node;
        this.offset = (ValueNode)getNodeFactory().copyNode(other.offset,
                                                           getParserContext());
        this.fetchFirst = (ValueNode)getNodeFactory().copyNode(other.fetchFirst,
                                                               getParserContext());
    }

    /**
     * Convert this object to a String.  See comments in QueryTreeNode.java
     * for how this should be done for tree printing.
     *
     * @return  This object as a String
     */

    public String toString() {
        return "offset: " + offset + "\n" +
            "fetchFirst:" + fetchFirst + "\n" +
            super.toString();
    }

}

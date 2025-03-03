/*
 * Copyright (c) 2018. Manuel D. Rossetti, rossetti@uark.edu
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package jsl.utilities.random.sp;

import jsl.utilities.random.SampleIfc;

/**
 * @author rossetti
 */
public interface TwoStateMarkovChainIfc extends SampleIfc {

    int getInitialState();

    double getP0();

    double getP01();

    double getP1();

    double getP11();

    int getState();

    /**
     * Sets the state back to the initial state
     */
    void reset();

    void setInitialState(int initialState);

    void setProbabilities(double p11, double p01);

    void setState(int state);

}

/*
 *  BlinzEngine - A library for large 2D world simultions and games.
 *  Copyright (C) 2009  Blinz <gtalent2@gmail.com>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 3 as
 *  published by the Free Software Foundation.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.blinz.util.concurrency;

/**
 *
 * @author Blinz
 */
abstract class Task {

    boolean moveOn = false;
    TaskExecuter taskProcessor;

    /**
     * Initializes this Task for the TaskProcessor
     * @param threadCount
     * @return true if initialization suceeded, false otherwise.
     */
    synchronized boolean init(TaskExecuter taskProcessor) {
        if (this.taskProcessor == null) {
            this.taskProcessor = taskProcessor;
            return true;
        }
        return false;
    }

    /**
     * Dissassociates this Task with its current TaskExecuter.
     */
    final void drop() {
        taskProcessor = null;
    }

    /**
     * 
     * @return if true the TaskProcessor should move on, otherwise continue on this Task
     */
    boolean moveOn() {
        return moveOn;
    }

    void enter() {
        run();
    }

    void reset() {
        moveOn = false;
    }

    final void setMoveOn() {
        this.moveOn = true;
    }

    abstract void run();
}
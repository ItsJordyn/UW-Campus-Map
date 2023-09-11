/*
 * Copyright (C) 2022 Kevin Zatloukal.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

import React, {Component} from 'react';

interface EdgeListProps {
    onChange(edges: string[]): void;  // called when a new edge list is ready
                                 // TODO: once you decide how you want to communicate the edges to the App, you should
                                 // change the type of edges so it isn't `any`
}

interface EdgeListState {
    edges: string;
}

/**
 * A text field that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class EdgeList extends Component<EdgeListProps, EdgeListState> {
    constructor(props: any) {
        super(props);
        this.state = {
            edges : "",
        };
    }

    parseString(textContent: string): void {
        let mapEdges = [];
        for (const line of textContent.split("\n")) {
            if (this.validateLine(line)) {
                mapEdges.push(line);
            }
        }
        this.props.onChange(mapEdges);
    }

    validateLine(line: string): boolean {
        let lineComponents = line.split(" ");
        if (lineComponents.length !== 5) {
            return false;
        }

        for (let i = 0; i < 4; i++) {
            let coordinate = Number(lineComponents[i]);

            if (isNaN(coordinate)) {
                alert("error in line [" + line + "]: contains a non-number");
                return false;
            } else if (coordinate < 0 || coordinate > 4000) {
                alert("error in [" + line + "]: coordinates must be between 0 and 4000");
                return false;
            }
        }
        return true;
    }

    clear() {
        this.setState( {edges : ""})
        this.props.onChange([])
    }

    render() {
        return (
            <div id="edge-list">
                Edges <br/>
                <textarea
                    rows={5}
                    cols={30}
                    onChange={(event) => this.setState({edges : event.target.value})}
                    value={this.state.edges}
                /> <br/>
                <button onClick={() => {this.parseString(this.state.edges);}}>Draw</button>
                <button onClick={() => {this.clear()}}>Clear</button>
            </div>
        );
    }
}

export default EdgeList;

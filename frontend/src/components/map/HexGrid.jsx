import React from "react";
import "./Gridstyle.css";
import Hexagon from "./Hexagon";
import config from "/src/config";

function HexGrid() {
  let row = [];
  let finalRow = [];
  let grid = [];
  const width = config["width"];
  const height = config["height"];

  for (var i = 1; i <= width; i++) {
    row.push(<Hexagon />);
  }
  for (var j = 1; j <= height; j++) {
    let i = 1;
    finalRow = [...row.map((x, idx) => <Hexagon row={i} col={idx + 1} />)];
    grid.push(<div>{finalRow}</div>)
    finalRow=[]
    i++;
  }

  return <div>{grid}</div>;
}

export default HexGrid;

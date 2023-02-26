import React from "react";
import Hexagon from "./Hexagon";

function HexGrid() {
  const array = [
    ["nice", 7, 1, 9, 5, 3, 6, 8, 2, 4],
    [6, "nice", 2, 8, 1, 7, 9, 5, 4, 3],
    [9, 8, "nice", 2, 6, 4, 7, 1, 3, 8],
    [2, 1, 9, "nice", 7, 5, 4, 3, 8, 1],
    [3, 6, 7, 1, "nice", 2, 5, 4, 9, 6],
    [8, 5, 4, 3, 9, "nice", 1, 2, 7, 5],
    [7, 4, 3, 5, 2, 9, "nice", 6, 1, 4],
    [5, 9, 6, 4, 3, 1, 2, "nice", 5, 9],
    [1, 2, 8, 7, 4, 8, 3, 9, "nice", 2],
    [4, 3, 1, 9, 5, 7, 6, 8, 2, "nice"],
  ];
  return (
    <div>
      {array.map((row, rowIdx) => (
        <div key={rowIdx}>
          {row.map((regionData, regionIdx) => (
            <Hexagon row={rowIdx + 1} col={regionIdx + 1} value={regionData} />
          ))}
        </div>
      ))}
    </div>
  );
}

export default HexGrid;

import React from "react";
import "./Gridstyle.css";

export default function Hexagon(props) {
  return (
    <div class="hexagon">
      <div class="content">{props.row},{props.col}</div>
    </div>
  );
}

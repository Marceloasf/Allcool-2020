import React from "react";
import { Switch, Route } from "react-router-dom";
import { HomePage } from "../pages";

export const Routes = () => {
  return (
    <Switch>
      <Route path="/" component={HomePage} exact />
    </Switch>
  );
};

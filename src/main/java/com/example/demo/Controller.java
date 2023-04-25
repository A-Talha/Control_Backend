package com.example.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Component
@RestController
@CrossOrigin

public class Controller {
    @ResponseBody
    @RequestMapping(value = "/addshape",method = RequestMethod.POST)
    public ArrayList<ArrayList<Integer>> addShape(@RequestBody int[][] g) {
        Graph graph = new Graph(g);
        return graph.solve();
    }

}

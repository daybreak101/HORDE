package project.game.horde.zombieLogic;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import project.game.horde.main.Handler;
import project.game.horde.utils.Graph;
import project.game.horde.utils.Node;
import project.game.horde.utils.Utils;
import project.game.horde.worlds.World;

public class PathingLogic {

    private final Handler handler;
    private final World world;

    private Graph graph;
    private ArrayList<Node> nodes = new ArrayList<>();

    public PathingLogic(Handler handler, World world, String nodesPath, String edgesPath) {
        this.handler = handler;
        this.world = world;

        createNodes(nodesPath);
        for (Node n : nodes) {
            graph = new Graph(nodes.size());
            createEdges(edgesPath);
            buildGraph(n.getVertex());
        }
        writeToFile();
        distanceToEveryNode();

    }

    public void renderNodes(Graphics g) {
        g.setColor(Color.red);
        for (Node n : nodes) {
            g.fillOval((int) (n.getX() - handler.getGameCamera().getxOffset()),
                    (int) (n.getY() - handler.getGameCamera().getyOffset()), 5, 5);
            g.drawString(Integer.toString(n.getVertex()), (int) (n.getX() - handler.getGameCamera().getxOffset()),
                    (int) (n.getY() - handler.getGameCamera().getyOffset()));
            g.drawString(Integer.toString(n.getX()) + ", " + Integer.toString(n.getY()),
                    (int) (n.getX() - handler.getGameCamera().getxOffset()),
                    (int) (n.getY() + 10 - handler.getGameCamera().getyOffset()));
            for (Node m : nodes) {
                Node nextStep = n.getNextNode(m.getVertex());
                if (nextStep != null) {
                    g.drawLine((int) (n.getX() - handler.getGameCamera().getxOffset()),
                            (int) (n.getY() - handler.getGameCamera().getyOffset()),
                            (int) (nextStep.getX() - handler.getGameCamera().getxOffset()),
                            (int) (nextStep.getY() - handler.getGameCamera().getyOffset()));
                }
            }
        }

    }

    private void buildGraph(int src) {
        int[] path = graph.findPaths(src);
        for (int i = 0; i < path.length; i++) {
            // System.out.println("Source Node: " + src + ", Dest node: " + i + ", Next
            // Step: " + path[i]);
            if (i == src) {
                nodes.get(src).setNextNodes(null);
            } else if (path[i] == -1) {
                nodes.get(src).setNextNodes(null); 
            }else {
                nodes.get(src).setNextNodes(nodes.get(path[i]));
            }
        }
    }

    private void createEdges(String edgesPath) {
        // read file
        String file = Utils.loadFileAsString(edgesPath);
        String[] tokens = file.split("\\s+");

        // get number of nodes
        int i = 0;

        // process edges
        while (i < tokens.length) {
            int n1 = Utils.parseInt(tokens[i++]);
            int n2 = Utils.parseInt(tokens[i++]);
            Node m = nodes.get(n1);
            Node n = nodes.get(n2);
            float distance = Utils.getEuclideanDistance(m.getX(), m.getY(), n.getX(), n.getY());
            if (!world.checkForStaticEntities(m.getX(), m.getY(), n.getX(), n.getY())) {
                graph.createEdge(m.getVertex(), n.getVertex(), distance);
                graph.createEdge(n.getVertex(), m.getVertex(), distance);
            }
        }
    }

    public final void createNodes(String nodesPath) {
        // read file
        String file = Utils.loadFileAsString(nodesPath);
        String[] tokens = file.split("\\s+");

        // get number of nodes
        int i = 0;

        // process nodes
        int vertex, x, y, room, withinPlayable;
        while (i < tokens.length) {
            vertex = Utils.parseInt(tokens[i++]);
            x = Utils.parseInt(tokens[i++]);
            y = Utils.parseInt(tokens[i++]);
            room = Utils.parseInt(tokens[i++]);
            withinPlayable = Utils.parseInt(tokens[i++]);
            nodes.add(new Node(vertex, x, y, room, withinPlayable));
        }
    }

    public Graph getGraph() {
        return graph;
    }

    // make sure no static entities in between
    public int getClosestNode(float x, float y) {
        Node closestNode = null;
        float closestDistance = 2000000;
        float currentDistance;
        for (Node n : nodes) {
            if (!world.checkForStaticEntities((int) x, (int) y, n.getX(), n.getY())) {
                currentDistance = Utils.getEuclideanDistance(x, y, n.getX(), n.getY());
                if (closestNode == null || currentDistance < closestDistance) {
                    closestNode = n;
                    closestDistance = currentDistance;
                }
            }
        }

        if (closestNode == null) {
            closestNode = nodes.get(0);
        }
        return closestNode.getVertex();
    }

    public float getDistanceBetweenNodes(int startNode, int endNode) {
        String start = Integer.toString(startNode);
        String end = Integer.toString(endNode);
        String complete = start + ":" + end;
        return distances.get(complete);
    }

    HashMap<String, Float> distances = new HashMap<>();

    private void distanceToEveryNode() {
        String start;
        String end;
        String complete;
        Node sumNode;
        Node nextNode;
        float distance;
        for (Node n : nodes) {
            start = Integer.toString(n.getVertex());
            for (Node m : nodes) {
                distance = 0;
                end = Integer.toString(m.getVertex());
                // System.out.println("Computing distance from node " + start + " to node " +
                // end);
                complete = start + ":" + end;
                sumNode = n;
                if (sumNode.getNextNode(m.getVertex()) == null) {
                    // System.out.println("NEXT NODE OF " + sumNode.getVertex() + " IS NULL");
                    distances.put(complete, (float) Integer.MAX_VALUE);
                } else {
                    while (sumNode.getVertex() != m.getVertex()) {

                        nextNode = sumNode.getNextNode(m.getVertex());
                        if (sumNode == nextNode) {
                            nextNode = m;
                        }
                        // System.out.println(sumNode.getVertex() + ", " + nextNode.getVertex() + ", " +
                        // m.getVertex());
                        if (nextNode != null) {
                            distance += Utils.getEuclideanDistance(sumNode.getX(), sumNode.getY(), nextNode.getX(),
                                    nextNode.getY());
                        }
                        sumNode = nextNode;
                    }
                    distances.put(complete, distance);
                }
            }
        }
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    private void writeToFile() {
        try {
            FileWriter writer = new FileWriter("res/paths.txt");
            BufferedWriter buffer = new BufferedWriter(writer);

            for (Node n : nodes) {
                for (Node m : nodes) {
                    if (n.getNextNode(m.getVertex()) == null) {
                        buffer.write("Source: " + n.getVertex() + ", Dest: " + m.getVertex() + ", Next Node: -1");
                    } else {
                        buffer.write("Source: " + n.getVertex() + ", Dest: " + m.getVertex() + ", Next Node: "
                                + n.getNextNode(m.getVertex()).getVertex());
                    }

                    buffer.newLine();
                }
            }

            buffer.close();
        } catch (IOException e) {
        }
    }

    public Node getNextStep(int src, int dest) {
        // System.out.println("Source Node: " + src + ", Dest node: " + dest + ", Next
        // Step: " + nodes.get(src).getNextNode(dest).getVertex());

        return nodes.get(src).getNextNode(dest);

    }

}

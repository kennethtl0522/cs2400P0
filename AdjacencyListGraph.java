import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class AdjacencyListGraph<E> {
	private static class Edge {
		int vertexIndex;
		int weight;
		Edge nextEdge;
 
		public Edge(int vertexIndex, int weight) {
			this.vertexIndex = vertexIndex;
			this.weight = weight;
		}
	}
 
	// 邻接表中表的顶点
	private static class Vertex<E> {
		E data;     
		Edge nextEdge;
	};
 
	private Vertex<E>[] vertices;
	private int numVertices;
	private int maxNumVertices;
    private boolean[] visited;

	@SuppressWarnings("unchecked")
	public AdjacencyListGraph(int maxNumVertices) {
		this.maxNumVertices = maxNumVertices;
		vertices = (Vertex<E>[]) Array.newInstance(Vertex.class, maxNumVertices);
	}
 
	// 得到顶点的数目
	public int getNumOfVertex() {
		return numVertices;
	}
 
	// 插入顶点
	public boolean insertVertex(E v) {
		if (numVertices >= maxNumVertices)
			return false;
		Vertex<E> vertice = new Vertex<E>();
		vertice.data = v;
		vertices[numVertices++] = vertice;
		return true;
	}
 
	// 删除顶点
	public boolean deleteVex(E v) {
		for (int i = 0; i < numVertices; i++) {
			if (vertices[i].data.equals(v)) {
				for (int j = i; j < numVertices - 1; j++) {
					vertices[j] = vertices[j + 1];
				}
				vertices[numVertices - 1] = null;
				numVertices--;
				Edge current;
				Edge previous;
				for (int j = 0; j < numVertices; j++) {
					if (vertices[j].nextEdge == null)
						continue;
					if (vertices[j].nextEdge.vertexIndex == i) {
						vertices[j].nextEdge = null;
						continue;
					}
					current = vertices[j].nextEdge;
					while (current != null) {
						previous = current;
						current = current.nextEdge;
						if (current != null && current.vertexIndex == i) {
							previous.nextEdge = current.nextEdge;
							break;
						}
					}
				}
				for (int j = 0; j < numVertices; j++) {
					current = vertices[j].nextEdge;
					while (current != null) {
						if (current.vertexIndex > i)
							current.vertexIndex--;
						current = current.nextEdge;
					}
				}
				return true;
			}
		}
		return false;
	}
 
	public int getIndex(E v) {
		for (int i = 0; i < numVertices; i++) {
			if (vertices[i].data.equals(v)) {
				return i;
			}
		}
		return -1;
	}

    public int getIndex(Vertex<E> v) {
        return getIndex(v.data);
    }
 
	public E getValue(int v) {
		if (v < 0 || v >= numVertices)
			return null;
		return vertices[v].data;
	}
 
	public boolean insertEdge(int v1, int v2, int weight) {
		if (v1 < 0 || v2 < 0 || v1 >= numVertices || v2 >= numVertices)
			throw new ArrayIndexOutOfBoundsException();
		Edge vertice1 = new Edge(v2, weight);
		if (vertices[v1].nextEdge == null) {
			vertices[v1].nextEdge = vertice1;
		} else {
			vertice1.nextEdge = vertices[v1].nextEdge;
			vertices[v1].nextEdge = vertice1;
		}
		Edge vertice2 = new Edge(v1, weight);
		if (vertices[v2].nextEdge == null) {
			vertices[v2].nextEdge = vertice2;
		} else {
			vertice2.nextEdge = vertices[v2].nextEdge;
			vertices[v2].nextEdge = vertice2;
		}
		return true;
	}
 
	public boolean deleteEdge(int v1, int v2) {
		if (v1 < 0 || v2 < 0 || v1 >= numVertices || v2 >= numVertices)
			throw new ArrayIndexOutOfBoundsException();
		Edge current = vertices[v1].nextEdge;
		Edge previous = null;
		while (current != null && current.vertexIndex != v2) {
			previous = current;
			current = current.nextEdge;
		}
		if (current != null)
			previous.nextEdge = current.nextEdge;
		current = vertices[v2].nextEdge;
		while (current != null && current.vertexIndex != v1) {
			previous = current;
			current = current.nextEdge;
		}
		if (current != null)
			previous.nextEdge = current.nextEdge;
		return true;
	}
 
	public int getEdge(int v1, int v2) {
		if (v1 < 0 || v2 < 0 || v1 >= numVertices || v2 >= numVertices)
			throw new ArrayIndexOutOfBoundsException();
		Edge current = vertices[v1].nextEdge;
		while (current != null) {
			if (current.vertexIndex == v2) {
				return current.weight;
			}
			current = current.nextEdge;
		}
		return 0;
	}


    public int getNumOfEdges(Vertex<E> v) {
        Edge curr = v.nextEdge;
        int count = 1;
        while (curr.nextEdge != null) {
            curr = curr.nextEdge;
            count++;
        }
        return count;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Vertex<E> vertex : vertices) {
            sb.append("Vertex " + vertex.data + "\n");
            Edge curr = vertex.nextEdge;
            sb.append(" " + getNumOfEdges(vertex) + " adjacencies: (" + getValue(curr.vertexIndex) + ", " + curr.weight + ") ");
            while (curr.nextEdge != null) {
                curr = curr.nextEdge;
                sb.append("(" + getValue(curr.vertexIndex) + ", " + curr.weight + ") ");
            }
            sb.append("\n");
        }
        return sb.toString();
      }


 
	public String depthFirstSearch(int v) {
		if (v < 0 || v >= numVertices)
			throw new ArrayIndexOutOfBoundsException();
		visited = new boolean[numVertices];
		StringBuilder sb = new StringBuilder();
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(v);
		visited[v] = true;
		Edge current;
		while (!stack.isEmpty()) {
			v = stack.pop();
			sb.append(vertices[v].data + ",");
			current = vertices[v].nextEdge;
			while (current != null) {
				if (!visited[current.vertexIndex]) {
					stack.push(current.vertexIndex);
					visited[current.vertexIndex] = true;
				}
				current = current.nextEdge;
			}
		}
		return sb.length() > 0 ? sb.substring(0, sb.length() - 1) : null;
	}
 
    public String breadthFirstSearch(int v) {
		if (v < 0 || v >= numVertices)
			throw new ArrayIndexOutOfBoundsException();
		visited = new boolean[numVertices];
		StringBuilder sb = new StringBuilder();
		Queue<Integer> queue = new LinkedList<Integer>();
		queue.offer(v);
		visited[v] = true;
		Edge current;
		while (!queue.isEmpty()) {
			v = queue.poll();
			sb.append(vertices[v].data + ",");
			current = vertices[v].nextEdge;
			while (current != null) {
				if (!visited[current.vertexIndex]) {
					queue.offer(current.vertexIndex);
					visited[current.vertexIndex] = true;
				}
				current = current.nextEdge;
			}
		}
		return sb.length() > 0 ? sb.substring(0, sb.length() - 1) : null;
	}
 
    public int[] dijkstra(int origin) {
		if (origin < 0 || origin >= numVertices)
			throw new ArrayIndexOutOfBoundsException();
		boolean[] set = new boolean[numVertices];// 默认初始为false
		int[] distance = new int[numVertices];// 存放源点到其他点的距离
		for (int i = 0; i < numVertices; i++) {
			distance[i] = Integer.MAX_VALUE;
		}
		Edge current; //current 等于 origin 的下一个edge
		current = vertices[origin].nextEdge;
		while (current != null) {
			distance[current.vertexIndex] = current.weight;
			current = current.nextEdge;
		}
		//排除自身距离，设为0
		distance[origin] = 0;
		set[origin] = true;
		// 处理从源点到其余顶点的最短路径
		for (int i = 0; i < numVertices; i++) {
			int min = Integer.MAX_VALUE;
			int index = -1;
			// 对比原点到每一个vertex的距离，并且记录最短的路径和index
			for (int j = 0; j < numVertices; j++) {
				if (set[j] == false) {
					if (distance[j] < min) {
						index = j;
						min = distance[j];
					}
				}
			}
			System.out.println(index + ": " + min);
			// 找到源点到索引为index顶点的最短路径长度
			if (index != -1) {
				set[index] = true;
				// 更新当前最短路径及距离     
				for (int w = 0; w < numVertices; w++) {
					if (set[w] == false) {	//取确认了最短路径的顶点的连接顶点信息
						current = vertices[index].nextEdge;
					while (current != null) {	//如果确认的顶点可以到未确认的顶点，则重新计算是否有最短路径
						if (current.vertexIndex == w) {
							if ((min + current.weight) < distance[w]) {
								distance[w] = (int) (min + current.weight);
								System.out.println("LOOP" + i + w + ": BREAK!");
								break;
							}
						}
						current = current.nextEdge;
						}
					}
				}
			}
		}
		return distance;
	}

    public void transitiveClosure() {
        for (Vertex<E> k : vertices) {
            for (Vertex<E> i : vertices) {
                if (k != i && getEdge(getIndex(i), getIndex(k)) != 0) {
                    for (Vertex<E> j : vertices) {
                        if (i != j && j != k && getEdge(getIndex(k), getIndex(j)) != 0) {
                            if (getEdge(getIndex(i), getIndex(j)) != 0){
                                insertEdge(getIndex(i), getIndex(j), -1);
                            }
                        }
                    }
                }
            }
        }
    }
}
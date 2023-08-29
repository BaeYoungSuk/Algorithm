import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static int[] dr = { -1, 1, 0, 0 };
	static int[] dc = { 0, 0, 1, -1 };

	static int[][] board;
	static int[][][] dist;
	static int n;
	static int m;

	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());

		board = new int[n][m];
		dist = new int[n][m][2];

		for (int row = 0; row < n; row++) {
			String next = br.readLine();
			for (int col = 0; col < m; col++) {
				board[row][col] = next.charAt(col) - '0';
			}
		}

		System.out.println(bfs());

	}

	public static int bfs() {

		// 순회하면서 dist 배열의 값을 모두 -1로 초기화한다.
		for (int row = 0; row < n; row++) {
			for (int col = 0; col < m; col++) {
				dist[row][col][0] = dist[row][col][1] = -1;
			}
		}

		// 시작 위치는 1로 지정한다.
		dist[0][0][0] = dist[0][0][1] = 1;

		Queue<int[]> queue = new LinkedList<>();

		queue.offer(new int[] { 0, 0, 0 });

		while (!queue.isEmpty()) {
			int[] cur = queue.poll();
			int curRow = cur[0];
			int curCol = cur[1];
			int broken = cur[2];

			// 만약 큐에서 나온 값이 도착지면 값을 반환한다.
			if (curRow == n - 1 && curCol == m - 1)
				return dist[curRow][curCol][broken];

			// dist 배열은 해당 노드까지의 거리를 나타낸다.
			// nextDist 값은 현재 노드 값보다 하나 더 크다.
			int nextDist = dist[curRow][curCol][broken] + 1;
			for (int i = 0; i < 4; i++) {
				int nr = curRow + dr[i];
				int nc = curCol + dc[i];
				if (OOB(nr, nc))
					continue;

				// 다음 노드가 0이고 아직 방문하지 않았다면
				if (board[nr][nc] == 0 && dist[nr][nc][broken] == -1) {

					// 현재 위치의 값에서 1을 추가해준다.
					dist[nr][nc][broken] = nextDist;
					queue.offer(new int[] { nr, nc, broken });
				}

				// 아직 벽을 부수지 않았고, 다음 노드가 1이고, 아직 방문하지 않았다면
				if (broken == 0 && board[nr][nc] == 1 && dist[nr][nc][1] == -1) {

					// 현재 위치의 값에서 1을 추가해준다.
					dist[nr][nc][1] = nextDist;
					queue.offer(new int[] { nr, nc, 1 });
				}
			}

		}
		return -1;

	}

	public static boolean OOB(int row, int col) {
		return row < 0 || row >= n || col < 0 || col >= m;
	}

}

//@author A0126730M
package whatsupnext.structure.util;

import java.util.Comparator;

public class TaskComparators {

	private static boolean hasEmptyEndTime(Task task) {
		return task.getEndTime() == null || task.getEndTime().isEmpty();
	}

	public static class TaskDefaultComparator implements Comparator<Task> {
		@Override
		public int compare(Task taskA, Task taskB) {
			// Floating tasks get sorted alphabetically on description
			// Floating tasks get placed before deadline or time frame tasks
			if (hasEmptyEndTime(taskA) && hasEmptyEndTime(taskB)) {
				return taskA.getDescription().compareToIgnoreCase(taskB.getDescription());
			} else if (hasEmptyEndTime(taskA) && !hasEmptyEndTime(taskB)) {
				return -1;
			} else if (!hasEmptyEndTime(taskA) && hasEmptyEndTime(taskB)) {
				return 1;
			}

			// Other tasks get sorted by end time and alphabetically on description
			long taskAEndTime = Long.parseLong(taskA.getEndTime());
			long taskBEndTime = Long.parseLong(taskB.getEndTime());

			if (taskAEndTime < taskBEndTime) {
				return -1;
			} else if (taskAEndTime > taskBEndTime) {
				return 1;
			} else {
				return taskA.getDescription().compareToIgnoreCase(taskB.getDescription());
			}
		}
	}
	
}
